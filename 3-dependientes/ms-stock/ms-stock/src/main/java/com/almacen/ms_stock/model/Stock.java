package com.almacen.ms_stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "producto_id",
            nullable = false,
            unique = true
    )
    private Long productoId;

    @Column(name = "stock_actual")
    private Integer stockActual;

    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    @Column(name = "stock_maximo")
    private Integer stockMaximo;

    private Boolean estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {

        this.fechaCreacion = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = true;
        }
    }
}