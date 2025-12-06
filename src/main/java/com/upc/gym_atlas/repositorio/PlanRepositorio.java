package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.Plan;
import com.upc.gym_atlas.entidades.enums.EstadoPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

public interface PlanRepositorio extends JpaRepository<Plan, Integer> {

    //Buscar por estado (activo / inactivo)
    List<Plan> findByEstado(EstadoPlan estadoPlan);

    //Buscar planes que contengan texto en el nombre
    List<Plan> findByNombrePlanContainingIgnoreCase(String nombrePlan);

    //Buscar planes con precio <= x
    List<Plan> findByPrecioLessThanEqual(BigDecimal precioMaximo);

    // Buscar planes con precio entre un mínimo y un máximo
    List<Plan> findByPrecioBetween(BigDecimal precioMinimo, BigDecimal precioMaximo);

    // Buscar planes por duración mínima (por ejemplo, planes de más de 30 días)
    List<Plan> findByDuracionDiasGreaterThanEqual(Integer duracionMinima);
}
