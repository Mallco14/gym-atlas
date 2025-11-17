package com.upc.gym_atlas.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;   // SERIAL PRIMARY KEY

    @Column(name = "dni", length = 8, nullable = false, unique = true)
    private String dni;

    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "contacto_emergencia", length = 200)
    private String contactoEmergencia;

    @Column(name = "notas_medicas", columnDefinition = "TEXT")
    private String notasMedicas;

    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    // Dejas que la BD ponga el CURRENT_TIMESTAMP, por eso insertable=false/updatable=false
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // Podrías manejarla con triggers o en la app; por ahora solo la mapeamos
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
