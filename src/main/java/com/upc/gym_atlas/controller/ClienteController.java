package com.upc.gym_atlas.controller;

import com.upc.gym_atlas.entidades.Cliente;
import com.upc.gym_atlas.negocio.IClienteServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final IClienteServicio clienteServicio;

    public ClienteController(IClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    //Listar Todos
    @GetMapping
    public ResponseEntity<List<Cliente>> listar(){
        return ResponseEntity.ok(clienteServicio.listar());
    }

    //Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id) {
        return clienteServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por Dni
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Cliente> obtenerPorDni(@PathVariable String dni) {
        return clienteServicio.obtenerPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear Cliente
    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteServicio.registrar(cliente);
        return ResponseEntity.ok(nuevo);
    }

    // Actualizar Cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id,
                                              @RequestBody Cliente datos) {
        Cliente actualizado = clienteServicio.actualizar(id, datos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar Cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
