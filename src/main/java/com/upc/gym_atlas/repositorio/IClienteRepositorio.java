package com.upc.gym_atlas.repositorio;

import com.upc.gym_atlas.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
