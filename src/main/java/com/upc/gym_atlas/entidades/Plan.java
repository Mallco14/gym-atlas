package com.upc.gym_atlas.entidades;

import com.upc.gym_atlas.entidades.enums.EstadoPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="planes")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlan;

    @NotBlank(message = "El nombre del plan es obligatorio")
    @Size(max = 50, message = "El nombre del plan no debe superar los 50 caracteres")
    @Column(name = "nombre_plan", length = 50, nullable = false)
    private String nombrePlan;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    @Column(name = "precio", precision = 8, scale = 2, nullable = false)
    private BigDecimal precio;

    @NotNull(message = "La duración en días es obligatoria")
    @Min(value = 1, message = "El plan debe durar al menos 1 día")
    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    @Column(name = "acceso_clases")
    private Boolean accesoClases = false;

    @Column(name = "acceso_maquinas")
    private Boolean accesoMaquinas = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPlan estado = EstadoPlan.activo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
