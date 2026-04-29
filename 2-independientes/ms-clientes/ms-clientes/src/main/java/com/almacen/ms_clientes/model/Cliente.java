package com.almacen.ms_clientes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String apodo;

    @NotBlank
    private String telefono;

    @Min(0)
    @Column(nullable = false)
    private Long deudaActual;

    @Min(0)
    @Column(nullable = false)
    private Long limiteCredito; //cuanto le damos de margen para fiar

}