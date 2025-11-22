package com.upc.gym_atlas.negocio.impl;

import com.upc.gym_atlas.entidades.Cliente;
import com.upc.gym_atlas.negocio.IClienteServicio;
import com.upc.gym_atlas.repositorio.IClienteRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServicioImpl implements IClienteServicio {
    private final IClienteRepositorio clienteRepositorio;

    public ClienteServicioImpl(IClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public Cliente registrar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Optional<Cliente> obtenerPorId(Integer idCliente) {
        return clienteRepositorio.findById(idCliente);
    }

    @Override
    public Optional<Cliente> obtenerPorDni(String dni) {
        return clienteRepositorio.findByDni(dni);
    }

    @Override
    public Cliente actualizar(Integer idCliente, Cliente datos) {
        return clienteRepositorio.findById(idCliente)
                .map(existente -> {
                    existente.setDni(datos.getDni());
                    existente.setNombres(datos.getNombres());
                    existente.setApellidos(datos.getApellidos());
                    existente.setEmail(datos.getEmail());
                    existente.setTelefono(datos.getTelefono());
                    existente.setFechaNacimiento(datos.getFechaNacimiento());
                    existente.setDireccion(datos.getDireccion());
                    existente.setContactoEmergencia(datos.getContactoEmergencia());
                    existente.setNotasMedicas(datos.getNotasMedicas());
                    existente.setFotoUrl(datos.getFotoUrl());
                    //
                    return clienteRepositorio.save(existente);
                })
                .orElse(null);
    }

    @Override
    public void eliminar(Integer idCliente) {
        clienteRepositorio.deleteById(idCliente);
    }
}
