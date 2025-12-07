package com.upc.gym_atlas.controller;

import com.upc.gym_atlas.entidades.Membresia;
import com.upc.gym_atlas.entidades.enums.EstadoMembresia;
import com.upc.gym_atlas.negocio.IMembresiaServicio;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/membresias")
public class MembresiaController {
    private final IMembresiaServicio membresiaServicio;

    public MembresiaController(IMembresiaServicio membresiaServicio) {
        this.membresiaServicio = membresiaServicio;
    }

    // ==========================
    // CRUD BÁSICO
    // ==========================

    // Crear membresía
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Membresia membresia) {
        try {
            Membresia creada = membresiaServicio.registrar(membresia);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeRespuesta(e.getMessage()));
        }
    }

    // Listar todas las membresías
    @GetMapping
    public ResponseEntity<List<Membresia>> listar() {
        return ResponseEntity.ok(membresiaServicio.listar());
    }

    // Obtener membresía por ID
    @GetMapping("/{id}")
    public ResponseEntity<Membresia> obtenerPorId(@PathVariable Integer id) {
        Optional<Membresia> opt = membresiaServicio.obtenerPorId(id);
        return opt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar membresía
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @Valid @RequestBody Membresia datos) {
        Membresia actualizada = membresiaServicio.actualizar(id, datos);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    // ==========================
    // POR CLIENTE / ESTADO
    // ==========================

    // Listar membresías por cliente
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Membresia>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(membresiaServicio.listarPorCliente(idCliente));
    }

    // Obtener membresía activa de un cliente (si existe)
    @GetMapping("/cliente/{idCliente}/activa")
    public ResponseEntity<Membresia> obtenerMembresiaActiva(@PathVariable Integer idCliente) {
        Optional<Membresia> opt = membresiaServicio.obtenerMembresiaActiva(idCliente);
        return opt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar por estado (activa, vencida, cancelada)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Membresia>> listarPorEstado(@PathVariable EstadoMembresia estado) {
        return ResponseEntity.ok(membresiaServicio.listarPorEstado(estado));
    }

    // ==========================
    // VENCIMIENTOS / ESTADO
    // ==========================

    // Buscar membresías que vencen entre dos fechas
    @GetMapping("/vencen")
    public ResponseEntity<List<Membresia>> buscarPorRangoVencimiento(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        return ResponseEntity.ok(membresiaServicio.buscarPorRangoVencimiento(desde, hasta));
    }

    // Cambiar estado manualmente
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id,
                                           @RequestParam("estado") EstadoMembresia nuevoEstado) {
        Membresia actualizada = membresiaServicio.cambiarEstado(id, nuevoEstado);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    // Actualizar automáticamente membresías vencidas (activa → vencida)
    @PostMapping("/actualizar-vencidas")
    public ResponseEntity<MensajeRespuesta> actualizarVencidas() {
        int cantidad = membresiaServicio.actualizarMembresiasVencidas();
        return ResponseEntity.ok(new MensajeRespuesta(
                "Membresías actualizadas a vencidas: " + cantidad
        ));
    }

    // ==========================
    // DTO simple para mensajes
    // ==========================

    public record MensajeRespuesta(String mensaje) { }
}
