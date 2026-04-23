package com.almacen.ms_productos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false, unique = true)
    private Long idCategoria;   // relacion con ms-categoria

    @Column(nullable = false)
    private Double precioVenta;

    @Column(nullable = false)
    private String unidadMedida; // litros, unidades , etc

    @Column(nullable = false)
    private String contenidoNeto; //    500 ml, 5 litros, gramos etc


}
