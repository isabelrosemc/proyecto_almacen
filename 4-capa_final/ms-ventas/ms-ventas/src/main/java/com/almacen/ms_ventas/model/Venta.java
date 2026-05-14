package com.almacen.ms_ventas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    private BigDecimal total;

    private Boolean estado;

    @OneToMany(
            mappedBy = "venta",
            cascade = CascadeType.ALL
    )
    private List<DetalleVenta> detalles;

    @PrePersist
    public void prePersist() {

        this.fechaVenta = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = true;
        }
    }
}