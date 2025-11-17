package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDni(String dni);
}
