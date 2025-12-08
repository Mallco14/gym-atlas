package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IngresoRepositorio extends JpaRepository<Ingreso, Integer> {
    // Todos los ingresos de un cliente
    List<Ingreso> findByCliente_IdCliente(Integer idCliente);

    // Ingresos por fecha exacta (para reportes diarios)
    List<Ingreso> findByFechaIngreso(LocalDate fecha);

    // Ingresos en un rango de fechas
    List<Ingreso> findByFechaIngresoBetween(LocalDate desde, LocalDate hasta);
}
