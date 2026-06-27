package com.almacen.ms_compras.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proveedor_id")
    private Long proveedorId;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;

    private BigDecimal total;

    private Boolean estado;

    @OneToMany(
            mappedBy = "compra",
            cascade = CascadeType.ALL
    )
    private List<DetalleCompra> detalles;

    @PrePersist
    public void prePersist() {

        this.fechaCompra = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = true;
        }
    }
}