package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.UsuariosSistema;
import com.upc.gym_atlas.entidades.enums.EstadoUsuario;
import com.upc.gym_atlas.entidades.enums.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioSistemaRepositorio extends JpaRepository<UsuariosSistema, Integer> {

    // Buscar por username para login
    Optional<UsuariosSistema> findByUsername(String username);

    // Filtrar por rol (admin, recepcionista, entrenador)
    List<UsuariosSistema> findByRol(RolUsuario rol);

    // Filtrar por estado (activo, inactivo)
    List<UsuariosSistema> findByEstado(EstadoUsuario estado);
}
