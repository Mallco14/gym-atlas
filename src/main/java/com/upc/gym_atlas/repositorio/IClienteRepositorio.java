package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDni(String dni);
}
