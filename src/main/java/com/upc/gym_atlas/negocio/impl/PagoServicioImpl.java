package com.upc.gym_atlas.negocio.impl;

import com.upc.gym_atlas.entidades.Pago;
import com.upc.gym_atlas.entidades.enums.MetodoPago;
import com.upc.gym_atlas.negocio.IPagoServicio;
import com.upc.gym_atlas.repositorio.PagoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PagoServicioImpl implements IPagoServicio {

    private final PagoRepositorio pagoRepositorio;

    public PagoServicioImpl(PagoRepositorio pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }

    @Override
    public Pago registrar(Pago pago) {
        // Si no envían fecha, usar fecha actual
        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDate.now());
        }
        return pagoRepositorio.save(pago);
    }

    @Override
    public List<Pago> listar() {
        return pagoRepositorio.findAll();
    }

    @Override
    public Optional<Pago> obtenerPorId(Integer idPago) {
        return pagoRepositorio.findById(idPago);
    }

    @Override
    public List<Pago> listarPorCliente(Integer idCliente) {
        return pagoRepositorio.findByCliente_IdCliente(idCliente);
    }

    @Override
    public List<Pago> listarPorMembresia(Integer idMembresia) {
        return pagoRepositorio.findByMembresia_IdMembresia(idMembresia);
    }

    @Override
    public List<Pago> listarPorMetodo(MetodoPago metodoPago) {
        return pagoRepositorio.findByMetodoPago(metodoPago);
    }

    @Override
    public List<Pago> listarPorFecha(LocalDate fechaPago) {
        return pagoRepositorio.findByFechaPago(fechaPago);
    }

    @Override
    public List<Pago> listarPorRango(LocalDate desde, LocalDate hasta) {
        return pagoRepositorio.findByFechaPagoBetween(desde, hasta);
    }
}

