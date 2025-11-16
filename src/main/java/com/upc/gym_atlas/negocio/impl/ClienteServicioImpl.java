package com.upc.gym_atlas.negocio.impl;

import com.upc.gym_atlas.entidades.Cliente;
import com.upc.gym_atlas.negocio.IClienteServicio;
import com.upc.gym_atlas.repositorio.IClienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClienteServicioImpl implements IClienteServicio {
    private final IClienteRepositorio clienteRepositorio;

    // contador simple para generar CLI0001, CLI0002, ...
    private final AtomicInteger secuencia = new AtomicInteger(1);

    public ClienteServicioImpl(IClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }
    private String generarCodigoCliente() {
        int valor = secuencia.getAndIncrement();
        return "CLI" + String.format("%04d", valor);
    }
    @Override
    public Cliente registrar(Cliente cliente) {
        // Generamos el id solo si viene vacío
        if (cliente.getId() == null || cliente.getId().isBlank()) {
            cliente.setId(generarCodigoCliente());
        }
        return clienteRepositorio.save(cliente);
    }
    @Override
    public List<Cliente> listar() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Optional<Cliente> obtenerPorId(String id) {
        return clienteRepositorio.findById(Integer.valueOf(id));
    }

    @Override
    public Cliente actualizar(String id, Cliente datos) {
        return clienteRepositorio.findById(Integer.valueOf(id))
                .map(existente -> {
                    existente.setNombre(datos.getNombre());
                    existente.setDni(datos.getDni());
                    existente.setTelefono(datos.getTelefono());
                    existente.setActivo(datos.isActivo());
                    return clienteRepositorio.save(existente);
                })
                .orElse(null);
    }

    @Override
    public void eliminar(String id) {
        clienteRepositorio.deleteById(Integer.valueOf(id));
    }
}
