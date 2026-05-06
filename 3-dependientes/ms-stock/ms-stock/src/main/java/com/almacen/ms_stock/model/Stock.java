package com.almacen.ms_stock.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity @Data
public class Stock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long idProducto; // Conexión ms-productos

    @Min(0)
    @Column(nullable = false)
    private Double cantidadActual;

    @Min(0)
    @Column(nullable = false)
    private Double cantidadMinima; // Umbral para alertas

    private String ubicacion; // Ejemplo: Pasillo A-1
}