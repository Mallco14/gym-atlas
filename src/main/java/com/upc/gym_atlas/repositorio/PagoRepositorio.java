package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.Pago;
import com.upc.gym_atlas.entidades.enums.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
    // Pagos por cliente
    List<Pago> findByCliente_IdCliente(Integer idCliente);

    // Pagos por membresía
    List<Pago> findByMembresia_IdMembresia(Integer idMembresia);

    // Pagos por método de pago
    List<Pago> findByMetodoPago(MetodoPago metodoPago);

    // Pagos por fecha exacta
    List<Pago> findByFechaPago(LocalDate fechaPago);

    // Pagos por rango de fechas
    List<Pago> findByFechaPagoBetween(LocalDate desde, LocalDate hasta);
}
