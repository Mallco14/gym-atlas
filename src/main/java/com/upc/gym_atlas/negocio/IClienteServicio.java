package com.upc.gym_atlas.negocio;

import com.upc.gym_atlas.entidades.Cliente;

import java.util.List;
import java.util.Optional;


public interface IClienteServicio {
    Cliente registrar(Cliente cliente);          // Crear (genera código CLI0001...)
    List<Cliente> listar();                     // Listar todos
    Optional<Cliente> obtenerPorId(String id);  // Buscar por id
    Cliente actualizar(String id, Cliente datos);
    void eliminar(String id);
}
