package com.upc.gym_atlas.negocio;

import com.upc.gym_atlas.entidades.Ingreso;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IIngresoServicio {
    Ingreso registrar(Ingreso ingreso);

    Ingreso registrarPorDni(String dniCliente, String recepcionista);

    List<Ingreso> listar();

    Optional<Ingreso> obtenerPorId(Integer idIngreso);

    List<Ingreso> listarPorCliente(Integer idCliente);

    List<Ingreso> listarPorFecha(LocalDate fecha);

    List<Ingreso> listarPorRango(LocalDate desde, LocalDate hasta);
}
