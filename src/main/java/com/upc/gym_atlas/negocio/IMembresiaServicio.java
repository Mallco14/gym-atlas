package com.upc.gym_atlas.negocio;

import com.upc.gym_atlas.entidades.Membresia;
import com.upc.gym_atlas.entidades.enums.EstadoMembresia;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IMembresiaServicio {

    //Registrar una nueva membresia
    Membresia registrar(Membresia membresia);

    //Listar todas
    List<Membresia> listar();

    //Obtener por ID
    Optional<Membresia> obtenerPorId(Integer idMembresia);

    // Listar por cliente
    List<Membresia> listarPorCliente(Integer idCliente);

    // Buscar membresía activa del cliente (la que está vigente en fecha actual)
    Optional<Membresia> obtenerMembresiaActiva(Integer idCliente);

    // Listar por estado
    List<Membresia> listarPorEstado(EstadoMembresia estado);

    // Marcar membresías vencidas automáticamente
    int actualizarMembresiasVencidas();

    // Buscar membresías que vencen entre fechas
    List<Membresia> buscarPorRangoVencimiento(LocalDate desde, LocalDate hasta);

    // Cambiar estado manualmente
    Membresia cambiarEstado(Integer idMembresia, EstadoMembresia nuevoEstado);

    // Actualizar una membresía existente
    Membresia actualizar(Integer idMembresia, Membresia datos);
}
