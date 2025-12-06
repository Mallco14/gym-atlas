package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.Membresia;
import com.upc.gym_atlas.entidades.enums.EstadoMembresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MembresiaRepositorio  extends JpaRepository<Membresia, Integer> {

    //Listar todas las membresias de un cliente
    List<Membresia> findByCliente_idCliente(Integer idCliente);

    //Listar por estado(activa, vencida, cancelada)
    List<Membresia> findByEstado(EstadoMembresia estado);

    //Buscar membresias de un cliente por estado
    List<Membresia> findByCliente_IdClienteAndEstado(Integer idCliente, EstadoMembresia estado);

    // Buscar membresías que vencen entre dos fechas (para recordatorios)
    List<Membresia> findByFechaFinBetween(LocalDate desde, LocalDate hasta);

    // Buscar membresías vencidas antes de una fecha (para marcar como vencidas)
    List<Membresia> findByFechaFinBeforeAndEstado(LocalDate fechaLimite, EstadoMembresia estado);

    // Buscar la última membresía de un cliente (por fecha_fin más reciente)
    Optional<Membresia> findFirstByCliente_IdClienteOrderByFechaFinDesc(Integer idCliente);
}
