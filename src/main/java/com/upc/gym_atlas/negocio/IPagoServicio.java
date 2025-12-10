package com.upc.gym_atlas.negocio;

import com.upc.gym_atlas.entidades.Pago;
import com.upc.gym_atlas.entidades.enums.MetodoPago;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPagoServicio {

    Pago registrar(Pago pago);

    List<Pago> listar();

    Optional<Pago> obtenerPorId(Integer id);

    List<Pago> listarPorCliente(Integer idCliente);

    List<Pago> listarPorMembresia(Integer idMembresia);

    List<Pago> listarPorMetodo(MetodoPago metodoPago);

    List<Pago> listarPorFecha(LocalDate fechaPago);

    List<Pago> listarPorRango(LocalDate desde, LocalDate hasta);

}
