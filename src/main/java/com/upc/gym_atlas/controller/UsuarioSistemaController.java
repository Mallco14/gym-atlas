package com.upc.gym_atlas.controller;

import com.upc.gym_atlas.entidades.UsuariosSistema;
import com.upc.gym_atlas.entidades.enums.EstadoUsuario;
import com.upc.gym_atlas.entidades.enums.RolUsuario;
import com.upc.gym_atlas.negocio.IUsuarioSistemaServicio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioSistemaController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioSistemaController.class);

    private final IUsuarioSistemaServicio usuarioServicio;

    public UsuarioSistemaController(IUsuarioSistemaServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // ==========================
    // CRUD BÁSICO
    // ==========================

    // Crear usuario (la contraseña viene en passwordHash en texto plano, el servicio la hashea)
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuariosSistema usuario) {
        try {
            logger.info("POST /api/usuarios → Registrar usuario: {}", usuario.getUsername());
            UsuariosSistema creado = usuarioServicio.registrar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            // Por ejemplo, username duplicado
            logger.warn("Error registrando usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MensajeRespuesta(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuariosSistema>> listar() {
        logger.info("GET /api/usuarios → Listar todos los usuarios");
        return ResponseEntity.ok(usuarioServicio.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuariosSistema> obtenerPorId(@PathVariable Integer id) {
        logger.info("GET /api/usuarios/{} → Buscar usuario por ID", id);
        Optional<UsuariosSistema> usuario = usuarioServicio.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuariosSistema> obtenerPorUsername(@PathVariable String username) {
        logger.info("GET /api/usuarios/username/{} → Buscar usuario por username", username);
        Optional<UsuariosSistema> usuario = usuarioServicio.obtenerPorUsername(username);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar datos generales (NO contraseña)
    @PutMapping("/{id}")
    public ResponseEntity<UsuariosSistema> actualizarDatos(@PathVariable Integer id,
                                                          @Valid @RequestBody UsuariosSistema datos) {
        logger.info("PUT /api/usuarios/{} → Actualizar datos del usuario", id);
        UsuariosSistema actualizado = usuarioServicio.actualizarDatos(id, datos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        logger.info("DELETE /api/usuarios/{} → Eliminar usuario", id);
        usuarioServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================
    // FILTROS POR ROL Y ESTADO
    // ==========================

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuariosSistema>> listarPorRol(@PathVariable RolUsuario rol) {
        logger.info("GET /api/usuarios/rol/{} → Listar usuarios por rol", rol);
        return ResponseEntity.ok(usuarioServicio.listarPorRol(rol));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UsuariosSistema>> listarPorEstado(@PathVariable EstadoUsuario estado) {
        logger.info("GET /api/usuarios/estado/{} → Listar usuarios por estado", estado);
        return ResponseEntity.ok(usuarioServicio.listarPorEstado(estado));
    }

    // ==========================
    // LOGIN (AUTENTICACIÓN BÁSICA)
    // ==========================

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        logger.info("POST /api/usuarios/login → Intento de login para username: {}", request.getUsername());
        Optional<UsuariosSistema> usuario = usuarioServicio.autenticar(request.getUsername(), request.getPassword());

        if (usuario.isEmpty()) {
            logger.warn("Login fallido para username: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MensajeRespuesta("Credenciales inválidas o usuario inactivo"));
        }

        logger.info("Login exitoso para username: {}", request.getUsername());
        return ResponseEntity.ok(usuario.get());
    }

    // ==========================
    // CAMBIO DE PASSWORD / ESTADO / ROL
    // ==========================

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> cambiarPassword(@PathVariable Integer id,
                                             @Valid @RequestBody PasswordChangeRequest request) {
        logger.info("PATCH /api/usuarios/{}/password → Cambio de contraseña", id);

        Optional<UsuariosSistema> usuario = usuarioServicio.obtenerPorId(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioServicio.cambiarPassword(id, request.getNuevaPassword());
        return ResponseEntity.ok(new MensajeRespuesta("Contraseña actualizada correctamente"));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id,
                                           @Valid @RequestBody EstadoChangeRequest request) {
        logger.info("PATCH /api/usuarios/{}/estado → Cambio de estado a {}", id, request.getEstado());

        Optional<UsuariosSistema> usuario = usuarioServicio.obtenerPorId(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioServicio.cambiarEstado(id, request.getEstado());
        return ResponseEntity.ok(new MensajeRespuesta("Estado actualizado correctamente"));
    }

    @PatchMapping("/{id}/rol")
    public ResponseEntity<?> cambiarRol(@PathVariable Integer id,
                                        @Valid @RequestBody RolChangeRequest request) {
        logger.info("PATCH /api/usuarios/{}/rol → Cambio de rol a {}", id, request.getRol());

        Optional<UsuariosSistema> usuario = usuarioServicio.obtenerPorId(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioServicio.cambiarRol(id, request.getRol());
        return ResponseEntity.ok(new MensajeRespuesta("Rol actualizado correctamente"));
    }

    // ==========================
    // DTOs internos para requests
    // ==========================

    @Data
    public static class LoginRequest {
        @NotBlank(message = "El username es obligatorio")
        private String username;

        @NotBlank(message = "La contraseña es obligatoria")
        private String password;
    }

    @Data
    public static class PasswordChangeRequest {
        @NotBlank(message = "La nueva contraseña es obligatoria")
        private String nuevaPassword;
    }

    @Data
    public static class EstadoChangeRequest {
        @NotNull(message = "El estado es obligatorio")
        private EstadoUsuario estado;
    }

    @Data
    public static class RolChangeRequest {
        @NotNull(message = "El rol es obligatorio")
        private RolUsuario rol;
    }

    @Data
    public static class MensajeRespuesta {
        private final String mensaje;
    }
}
