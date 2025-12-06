package com.upc.gym_atlas.negocio;

import com.upc.gym_atlas.entidades.Plan;
import com.upc.gym_atlas.entidades.enums.EstadoPlan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IPlanServicio {
    // Crear plan
    Plan registrar(Plan plan);

    // Listar todos
    List<Plan> listar();

    // Obtener por ID
    Optional<Plan> obtenerPorId(Integer idPlan);

    // Actualizar datos de un plan
    Plan actualizar(Integer idPlan, Plan datos);

    // Eliminar plan
    void eliminar(Integer idPlan);

    // Cambiar estado (activo / inactivo)
    Plan cambiarEstado(Integer idPlan, EstadoPlan nuevoEstado);

    // Listar por estado
    List<Plan> listarPorEstado(EstadoPlan estado);

    // Buscar por nombre (contiene texto, ignore case)
    List<Plan> buscarPorNombre(String nombreParcial);

    // Buscar por precio máximo
    List<Plan> buscarPorPrecioMax(BigDecimal precioMaximo);

    // Buscar por rango de precios
    List<Plan> buscarPorRangoPrecio(BigDecimal precioMinimo, BigDecimal precioMaximo);

    // Buscar por duración mínima
    List<Plan> buscarPorDuracionMinima(Integer duracionMinima);
}
