package com.upc.gym_atlas.controller;

import com.upc.gym_atlas.dto.IngresoPorDniRequest;
import com.upc.gym_atlas.entidades.Ingreso;
import com.upc.gym_atlas.negocio.IIngresoServicio;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    private final IIngresoServicio ingresoServicio;

    public IngresoController(IIngresoServicio ingresoServicio) {
        this.ingresoServicio = ingresoServicio;
    }

    // ==========================
    // 1) REGISTRO DE INGRESOS
    // ==========================

    /**
     * Registrar ingreso recibiendo un Ingreso completo.
     * Útil para pruebas o carga manual.
     */
    @PostMapping
    public ResponseEntity<Ingreso> registrar(@RequestBody Ingreso ingreso) {
        Ingreso creado = ingresoServicio.registrar(ingreso);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/registrar-por-dni")
    public ResponseEntity<?> registrarPorDni(@RequestBody IngresoPorDniRequest request,
                                             Authentication authentication) {
        try {
            // usuario logueado
            String usernameRecepcionista = authentication.getName();

            Ingreso ingreso = ingresoServicio.registrarPorDni(
                    request.getDniCliente(),
                    usernameRecepcionista
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(ingreso);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(new MensajeRespuesta(e.getMessage()));
        }
    }


    // ==========================
    // 2) CONSULTAS / REPORTES
    // ==========================

    @GetMapping
    public ResponseEntity<List<Ingreso>> listar() {
        return ResponseEntity.ok(ingresoServicio.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingreso> obtenerPorId(@PathVariable Integer id) {
        Optional<Ingreso> opt = ingresoServicio.obtenerPorId(id);
        return opt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Ingreso>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(ingresoServicio.listarPorCliente(idCliente));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Ingreso>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ingresoServicio.listarPorFecha(fecha));
    }

    // /api/ingresos/rango?desde=2025-12-01&hasta=2025-12-31
    @GetMapping("/rango")
    public ResponseEntity<List<Ingreso>> listarPorRango(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(ingresoServicio.listarPorRango(desde, hasta));
    }

    // DTO sencillo para mensajes de error/estado
    public record MensajeRespuesta(String mensaje) { }
}

