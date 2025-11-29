package com.upc.gym_atlas.negocio;

import com.upc.gym_atlas.entidades.UsuariosSistema;
import com.upc.gym_atlas.entidades.enums.EstadoUsuario;
import com.upc.gym_atlas.entidades.enums.RolUsuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioSistemaServicio {
    // Crear usuario (recibe contraseña en plano, se guarda hasheada)
    UsuariosSistema registrar(UsuariosSistema usuarioPlano);

    // Listar todos
    List<UsuariosSistema> listar();

    // Obtener por id
    Optional<UsuariosSistema> obtenerPorId(Integer idUsuario);

    // Obtener por username (para login, búsquedas, etc.)
    Optional<UsuariosSistema> obtenerPorUsername(String username);

    // Listar por rol (admin, recepcionista, entrenador)
    List<UsuariosSistema> listarPorRol(RolUsuario rol);

    // Listar por estado (activo, inactivo)
    List<UsuariosSistema> listarPorEstado(EstadoUsuario estado);

    // Actualizar datos generales (nombres, apellidos, estado, rol, etc.)
    UsuariosSistema actualizarDatos(Integer idUsuario, UsuariosSistema datos);

    // Cambiar contraseña (recibe contraseña en plano y la vuelve a hashear)
    void cambiarPassword(Integer idUsuario, String nuevaPasswordPlano);

    // Cambiar estado (activo/inactivo)
    void cambiarEstado(Integer idUsuario, EstadoUsuario nuevoEstado);

    // Cambiar rol
    void cambiarRol(Integer idUsuario, RolUsuario nuevoRol);

    // Eliminar usuario
    void eliminar(Integer idUsuario);

    // Autenticar (login básico): si username + passwordPlano son correctos, devuelve el usuario
    Optional<UsuariosSistema> autenticar(String username, String passwordPlano);
}
