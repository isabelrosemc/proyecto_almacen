package com.almacen.ms_proveedores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String nombreContacto; // nombre del vendedor 

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String categoriaRubro; // por ejemplo: Bebidas , lacteos, Abarrotes, etc

}
