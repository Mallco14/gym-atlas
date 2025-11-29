package com.upc.gym_atlas.negocio.impl;

import com.upc.gym_atlas.entidades.UsuariosSistema;
import com.upc.gym_atlas.entidades.enums.EstadoUsuario;
import com.upc.gym_atlas.entidades.enums.RolUsuario;
import com.upc.gym_atlas.negocio.IUsuarioSistemaServicio;
import com.upc.gym_atlas.repositorio.UsuarioSistemaRepositorio;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioSistemaServicioImpl implements IUsuarioSistemaServicio {

    private final UsuarioSistemaRepositorio usuarioRepositorio;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioSistemaServicioImpl(UsuarioSistemaRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UsuariosSistema registrar(UsuariosSistema usuariosPlano) {
        // Validar que el usuario no exista
        usuarioRepositorio.findByUsername(usuariosPlano.getUsername())
                .ifPresent(u ->{
                    throw new IllegalArgumentException("El usuario ya existe en el sistema");
                });

        // Ocultar contraseña
        String hash = passwordEncoder.encode(usuariosPlano.getPasswordHash());
        usuariosPlano.setPasswordHash(hash);

        // Si no viene estado, lo dejó activo por defecto
        if (usuariosPlano.getEstado() == null) {
            usuariosPlano.setEstado(EstadoUsuario.activo);
        }

        return usuarioRepositorio.save(usuariosPlano);
    }

    @Override
    public List<UsuariosSistema> listar() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Optional<UsuariosSistema> obtenerPorId(Integer idUsuario) {
        return usuarioRepositorio.findById(idUsuario);
    }

    @Override
    public Optional<UsuariosSistema> obtenerPorUsername(String username) {
        return usuarioRepositorio.findByUsername(username);
    }

    @Override
    public List<UsuariosSistema> listarPorRol(RolUsuario rol) {
        return usuarioRepositorio.findByRol(rol);
    }

    @Override
    public List<UsuariosSistema> listarPorEstado(EstadoUsuario estado) {
        return usuarioRepositorio.findByEstado(estado);
    }

    @Override
    public UsuariosSistema actualizarDatos(Integer idUsuario, UsuariosSistema datos) {
        return usuarioRepositorio.findById(idUsuario)
                .map(existente -> {
                    // No cambiamos username ni contraseña aquí
                    existente.setNombres(datos.getNombres());
                    existente.setApellidos(datos.getApellidos());
                    existente.setRol(datos.getRol() != null ? datos.getRol() : existente.getRol());
                    existente.setEstado(datos.getEstado() != null ? datos.getEstado() : existente.getEstado());
                    return usuarioRepositorio.save(existente);
                })
                .orElse(null);
    }

    @Override
    public void cambiarPassword(Integer idUsuario, String nuevaPasswordPlano) {
        usuarioRepositorio.findById(idUsuario)
                .ifPresent(existente -> {
                    String hashNuevo = passwordEncoder.encode(nuevaPasswordPlano);
                    existente.setPasswordHash(hashNuevo);
                    usuarioRepositorio.save(existente);
                });
    }

    @Override
    public void cambiarEstado(Integer idUsuario, EstadoUsuario nuevoEstado) {
        usuarioRepositorio.findById(idUsuario)
                .ifPresent(existente -> {
                    existente.setEstado(nuevoEstado);
                    usuarioRepositorio.save(existente);
                });
    }

    @Override
    public void cambiarRol(Integer idUsuario, RolUsuario nuevoRol) {
        usuarioRepositorio.findById(idUsuario)
                .ifPresent(existente -> {
                    existente.setRol(nuevoRol);
                    usuarioRepositorio.save(existente);
                });
    }

    @Override
    public void eliminar(Integer idUsuario) {
        usuarioRepositorio.deleteById(idUsuario);
    }

    @Override
    public Optional<UsuariosSistema> autenticar(String username, String passwordPlano) {
        // 1) Buscar usuario por username
        Optional<UsuariosSistema> optUsuario = usuarioRepositorio.findByUsername(username);

        if (optUsuario.isEmpty()) {
            return Optional.empty();
        }

        UsuariosSistema usuario = optUsuario.get();

        // 2) Verificar estado
        if (usuario.getEstado() == EstadoUsuario.inactivo) {
            return Optional.empty();
        }

        // 3) Verificar contraseña
        boolean coincide = passwordEncoder.matches(passwordPlano, usuario.getPasswordHash());
        if (!coincide) {
            return Optional.empty();
        }

        return Optional.of(usuario);
    }
}
