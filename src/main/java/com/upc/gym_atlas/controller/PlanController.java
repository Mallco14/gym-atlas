package com.upc.gym_atlas.controller;


import com.upc.gym_atlas.entidades.Plan;
import com.upc.gym_atlas.entidades.enums.EstadoPlan;
import com.upc.gym_atlas.negocio.IPlanServicio;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
public class PlanController {
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

    private final IPlanServicio planServicio;

    public PlanController(IPlanServicio planServicio) {
        this.planServicio = planServicio;
    }

    // ==========================
    // CRUD BÁSICO
    // ==========================

    // Crear plan
    @PostMapping
    public ResponseEntity<Plan> crear(@Valid @RequestBody Plan plan) {
        logger.info("POST /api/planes → Crear plan: {}", plan.getNombrePlan());
        Plan creado = planServicio.registrar(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Listar todos los planes
    @GetMapping
    public ResponseEntity<List<Plan>> listar() {
        logger.info("GET /api/planes → Listar todos los planes");
        return ResponseEntity.ok(planServicio.listar());
    }

    // Obtener plan por ID
    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPorId(@PathVariable Integer id) {
        logger.info("GET /api/planes/{} → Obtener plan por ID", id);
        Optional<Plan> plan = planServicio.obtenerPorId(id);
        return plan.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar plan completo
    @PutMapping("/{id}")
    public ResponseEntity<Plan> actualizar(@PathVariable Integer id,
                                           @Valid @RequestBody Plan datos) {
        logger.info("PUT /api/planes/{} → Actualizar plan", id);
        Plan actualizado = planServicio.actualizar(id, datos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar plan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        logger.info("DELETE /api/planes/{} → Eliminar plan", id);
        planServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================
    // Filtros y búsquedas
    // ==========================

    // Listar por estado (activo / inactivo)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Plan>> listarPorEstado(@PathVariable EstadoPlan estado) {
        logger.info("GET /api/planes/estado/{} → Listar planes por estado", estado);
        return ResponseEntity.ok(planServicio.listarPorEstado(estado));
    }

    // Buscar por nombre (contiene texto)
    @GetMapping("/buscar")
    public ResponseEntity<List<Plan>> buscarPorNombre(@RequestParam(name = "nombre", required = false) String nombre) {
        logger.info("GET /api/planes/buscar?nombre={} → Buscar planes por nombre", nombre);
        return ResponseEntity.ok(planServicio.buscarPorNombre(nombre));
    }

    // Buscar por precio máximo
    @GetMapping("/precio/max")
    public ResponseEntity<List<Plan>> buscarPorPrecioMax(@RequestParam("valor") BigDecimal precioMaximo) {
        logger.info("GET /api/planes/precio/max?valor={} → Buscar planes con precio <= {}", precioMaximo, precioMaximo);
        return ResponseEntity.ok(planServicio.buscarPorPrecioMax(precioMaximo));
    }

    // Buscar por rango de precios
    @GetMapping("/precio/rango")
    public ResponseEntity<List<Plan>> buscarPorRangoPrecio(@RequestParam("min") BigDecimal precioMinimo,
                                                           @RequestParam("max") BigDecimal precioMaximo) {
        logger.info("GET /api/planes/precio/rango?min={}&max={} → Buscar planes por rango de precio",
                precioMinimo, precioMaximo);
        return ResponseEntity.ok(planServicio.buscarPorRangoPrecio(precioMinimo, precioMaximo));
    }

    // Buscar por duración mínima (>= días)
    @GetMapping("/duracion/minima")
    public ResponseEntity<List<Plan>> buscarPorDuracionMinima(@RequestParam("dias") Integer duracionMinima) {
        logger.info("GET /api/planes/duracion/minima?dias={} → Buscar planes por duración mínima", duracionMinima);
        return ResponseEntity.ok(planServicio.buscarPorDuracionMinima(duracionMinima));
    }

    // ==========================
    // Cambio de estado
    // ==========================

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Plan> cambiarEstado(@PathVariable Integer id,
                                              @RequestParam("estado") EstadoPlan nuevoEstado) {
        logger.info("PATCH /api/planes/{}/estado?estado={} → Cambiar estado de plan", id, nuevoEstado);
        Plan actualizado = planServicio.cambiarEstado(id, nuevoEstado);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }
}
