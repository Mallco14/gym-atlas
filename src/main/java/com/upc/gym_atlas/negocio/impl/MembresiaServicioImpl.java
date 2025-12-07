package com.upc.gym_atlas.negocio.impl;


import com.upc.gym_atlas.entidades.Membresia;
import com.upc.gym_atlas.entidades.enums.EstadoMembresia;
import com.upc.gym_atlas.negocio.IMembresiaServicio;
import com.upc.gym_atlas.repositorio.MembresiaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MembresiaServicioImpl implements IMembresiaServicio{
    private final MembresiaRepositorio membresiaRepositorio;

    public MembresiaServicioImpl(MembresiaRepositorio membresiaRepositorio) {
        this.membresiaRepositorio = membresiaRepositorio;
    }

    @Override
    public Membresia registrar(Membresia membresia) {
        // Si no se activa estado, por defecto activa
        if(membresia.getEstado()==null){
            membresia.setEstado(EstadoMembresia.activa);
        }

        //validar fecha inicio <= fecha fin
        if(membresia.getFechaInicio().isAfter(membresia.getFechaFin())){
            throw new IllegalArgumentException("La fecha Inicio no puede ser mayor a la fecha Fin");
        }

        return membresiaRepositorio.save(membresia);
    }
    @Override
    public List<Membresia> listar() {
        return membresiaRepositorio.findAll();
    }

    @Override
    public Optional<Membresia> obtenerPorId(Integer idMembresia) {
        return membresiaRepositorio.findById(idMembresia);
    }

    @Override
    public List<Membresia> listarPorCliente(Integer idCliente) {
        return membresiaRepositorio.findByCliente_idCliente(idCliente);
    }

    @Override
    public Optional<Membresia> obtenerMembresiaActiva(Integer idCliente) {
        LocalDate hoy = LocalDate.now();

        return membresiaRepositorio.findByCliente_idCliente(idCliente)
                .stream()
                .filter(m -> m.getEstado() == EstadoMembresia.activa)
                .filter(m -> !hoy.isBefore(m.getFechaInicio()) && !hoy.isAfter(m.getFechaFin()))
                .findFirst();
    }

    @Override
    public List<Membresia> listarPorEstado(EstadoMembresia estado) {
        return membresiaRepositorio.findByEstado(estado);
    }

    @Override
    public int actualizarMembresiasVencidas() {
        LocalDate hoy = LocalDate.now();

        List<Membresia> vencidas = membresiaRepositorio.findByFechaFinBeforeAndEstado(
                hoy,
                EstadoMembresia.activa
        );

        vencidas.forEach(m -> m.setEstado(EstadoMembresia.vencida));
        membresiaRepositorio.saveAll(vencidas);

        return vencidas.size(); // cantidad de membresías actualizadas
    }

    @Override
    public List<Membresia> buscarPorRangoVencimiento(LocalDate desde, LocalDate hasta) {
        return membresiaRepositorio.findByFechaFinBetween(desde, hasta);
    }

    @Override
    public Membresia cambiarEstado(Integer idMembresia, EstadoMembresia nuevoEstado) {
        return membresiaRepositorio.findById(idMembresia)
                .map(m -> {
                    m.setEstado(nuevoEstado);
                    return membresiaRepositorio.save(m);
                })
                .orElse(null);
    }

    @Override
    public Membresia actualizar(Integer idMembresia, Membresia datos) {
        return membresiaRepositorio.findById(idMembresia)
                .map(existente -> {
                    existente.setCliente(datos.getCliente());
                    existente.setPlan(datos.getPlan());
                    existente.setFechaInicio(datos.getFechaInicio());
                    existente.setFechaFin(datos.getFechaFin());
                    existente.setEstado(datos.getEstado());
                    return membresiaRepositorio.save(existente);
                })
                .orElse(null);
    }
}
