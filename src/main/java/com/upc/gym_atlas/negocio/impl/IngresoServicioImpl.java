package com.upc.gym_atlas.negocio.impl;

import com.upc.gym_atlas.entidades.Cliente;
import com.upc.gym_atlas.entidades.Ingreso;
import com.upc.gym_atlas.entidades.Membresia;
import com.upc.gym_atlas.entidades.UsuariosSistema;
import com.upc.gym_atlas.entidades.enums.EstadoMembresia;
import com.upc.gym_atlas.negocio.IIngresoServicio;
import com.upc.gym_atlas.negocio.IMembresiaServicio;
import com.upc.gym_atlas.repositorio.IClienteRepositorio;
import com.upc.gym_atlas.repositorio.IngresoRepositorio;
import com.upc.gym_atlas.repositorio.UsuarioSistemaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngresoServicioImpl implements IIngresoServicio {

    private final IngresoRepositorio ingresoRepositorio;
    private final IClienteRepositorio clienteRepositorio;
    private final IMembresiaServicio membresiaServicio;
    private final UsuarioSistemaRepositorio usuarioSistemaRepositorio;

    public IngresoServicioImpl(IngresoRepositorio ingresoRepositorio,
                               IClienteRepositorio clienteRepositorio,
                               IMembresiaServicio membresiaServicio,
                               UsuarioSistemaRepositorio usuarioSistemaRepositorio) {
        this.ingresoRepositorio = ingresoRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.membresiaServicio = membresiaServicio;
        this.usuarioSistemaRepositorio = usuarioSistemaRepositorio;
    }

    @Override
    public Ingreso registrar(Ingreso ingreso) {
        if (ingreso.getFechaIngreso() == null) {
            ingreso.setFechaIngreso(LocalDate.now());
        }
        if (ingreso.getHoraIngreso() == null) {
            ingreso.setHoraIngreso(LocalTime.now());
        }
        return ingresoRepositorio.save(ingreso);
    }

    @Override
    public Ingreso registrarPorDni(String dniCliente, String usernameRecepcionista) {

        // 1. Buscar cliente por DNI
        Cliente cliente = clienteRepositorio.findByDni(dniCliente)
                .orElseThrow(() -> new IllegalArgumentException("No existe cliente con DNI: " + dniCliente));

        // 2. Buscar recepcionista REAL en la tabla usuarios_sistema
        UsuariosSistema recepcionista = usuarioSistemaRepositorio.findByUsername(usernameRecepcionista)
                .orElseThrow(() -> new IllegalArgumentException("No existe usuario recepcionista: " + usernameRecepcionista));

        // 3. Validar membresía activa
        Optional<Membresia> membresiaActivaOpt = membresiaServicio.obtenerMembresiaActiva(cliente.getIdCliente());

        if (membresiaActivaOpt.isEmpty()) {
            throw new IllegalStateException("El cliente no tiene una membresía activa.");
        }

        Membresia membresiaActiva = membresiaActivaOpt.get();
        if (membresiaActiva.getEstado() != EstadoMembresia.activa) {
            throw new IllegalStateException("El cliente no tiene una membresía activa.");
        }

        // 4. Crear ingreso
        Ingreso ingreso = new Ingreso();
        ingreso.setCliente(cliente);
        ingreso.setRecepcionista(recepcionista); // UsuariosSistema
        ingreso.setFechaIngreso(LocalDate.now());
        ingreso.setHoraIngreso(LocalTime.now());

        return ingresoRepositorio.save(ingreso);
    }

    @Override
    public List<Ingreso> listar() {
        return ingresoRepositorio.findAll();
    }

    @Override
    public Optional<Ingreso> obtenerPorId(Integer idIngreso) {
        return ingresoRepositorio.findById(idIngreso);
    }

    @Override
    public List<Ingreso> listarPorCliente(Integer idCliente) {
        return ingresoRepositorio.findByCliente_IdCliente(idCliente);
    }

    @Override
    public List<Ingreso> listarPorFecha(LocalDate fecha) {
        return ingresoRepositorio.findByFechaIngreso(fecha);
    }

    @Override
    public List<Ingreso> listarPorRango(LocalDate desde, LocalDate hasta) {
        return ingresoRepositorio.findByFechaIngresoBetween(desde, hasta);
    }
}
