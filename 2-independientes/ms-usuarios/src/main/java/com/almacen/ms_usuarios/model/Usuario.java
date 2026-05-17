package com.almacen.ms_usuarios.model;

import com.almacen.ms_usuarios.model.enums.RolNombre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolNombre rol;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {

        fechaCreacion = LocalDateTime.now();

        if (estado == null) {
            estado = true;
        }
    }
}