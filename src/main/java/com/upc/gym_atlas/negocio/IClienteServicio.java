package com.upc.gym_atlas.negocio;

import com.upc.gym_atlas.entidades.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteServicio {
    Cliente registrar(Cliente cliente);

    List<Cliente> listar();

    Optional<Cliente> obtenerPorId(Integer idCliente);

    Optional<Cliente> obtenerPorDni(String dni);

    Cliente actualizar(Integer idCliente, Cliente datos);

    void eliminar(Integer idCliente);
}
