package com.upc.gym_atlas.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Cliente {
    @Id
    @Column(name = "id_cliente", nullable = false, unique = true, length = 8)
    private String id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "activo", nullable = false)
    private boolean activo;
}
