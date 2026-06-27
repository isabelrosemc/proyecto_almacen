package com.almacen.ms_pagos.service;

import com.almacen.ms_pagos.dto.PagoDTO;
import com.almacen.ms_pagos.exception.NotFoundException;
import com.almacen.ms_pagos.mapper.PagoMapper;
import com.almacen.ms_pagos.model.Pago;
import com.almacen.ms_pagos.repository.PagoRepository;
import com.almacen.ms_pagos.service.impl.PagoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para PagoServiceImpl.
 * PagoRepository queda simulado con Mockito.
 */
@ExtendWith(MockitoExtension.class)
class PagoServiceImplTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoServiceImpl pagoService;

    @Test
    void guardarPago_cuandoVentaNueva_deberiaGuardarYRetornarDTO() {

        // ARRANGE
        PagoDTO dto = new PagoDTO();
        dto.setMonto(150000.0);
        dto.setMetodoPago("TRANSFERENCIA");
        dto.setEstadoPago("APROBADO");
        dto.setIdVenta(5L);

        Pago entidad = new Pago();
        entidad.setIdPago(1L);
        entidad.setMonto(150000.0);
        entidad.setIdVenta(5L);

        PagoDTO expected = new PagoDTO();
        expected.setMonto(150000.0);
        expected.setIdVenta(5L);

        when(pagoRepository.existsByIdVenta(5L)).thenReturn(false);
        when(pagoRepository.save(any(Pago.class))).thenReturn(entidad);

        try (MockedStatic<PagoMapper> mapper = mockStatic(PagoMapper.class)) {
            mapper.when(() -> PagoMapper.toEntity(dto)).thenReturn(entidad);
            mapper.when(() -> PagoMapper.toDTO(entidad)).thenReturn(expected);

            // ACT
            PagoDTO resultado = pagoService.guardarPago(dto);

            // ASSERT
            assertThat(resultado.getMonto()).isEqualTo(150000.0);
        }

        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void guardarPago_cuandoVentaYaPagada_deberiaLanzarExcepcion() {

        // ARRANGE
        PagoDTO dto = new PagoDTO();
        dto.setIdVenta(5L);

        when(pagoRepository.existsByIdVenta(5L)).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> pagoService.guardarPago(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ya existe pago");

        verify(pagoRepository, never()).save(any());
    }

    @Test
    void buscarPago_cuandoExiste_deberiaRetornarDTO() {

        // ARRANGE
        Pago pago = new Pago();
        pago.setIdPago(1L);
        pago.setMonto(50000.0);

        PagoDTO dto = new PagoDTO();
        dto.setMonto(50000.0);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        try (MockedStatic<PagoMapper> mapper = mockStatic(PagoMapper.class)) {
            mapper.when(() -> PagoMapper.toDTO(pago)).thenReturn(dto);

            // ACT
            PagoDTO resultado = pagoService.buscarPago(1L);

            // ASSERT
            assertThat(resultado.getMonto()).isEqualTo(50000.0);
        }
    }

    @Test
    void buscarPago_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pagoService.buscarPago(99L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void listarPagos_deberiaRetornarListaDTOs() {

        // ARRANGE
        Pago pago = new Pago();
        pago.setIdPago(1L);
        PagoDTO dto = new PagoDTO();

        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        try (MockedStatic<PagoMapper> mapper = mockStatic(PagoMapper.class)) {
            mapper.when(() -> PagoMapper.toDTO(pago)).thenReturn(dto);

            // ACT
            List<PagoDTO> resultado = pagoService.listarPagos();

            // ASSERT
            assertThat(resultado).hasSize(1);
        }
    }

    @Test
    void eliminarPago_cuandoExiste_deberiaEliminar() {

        Pago pago = new Pago();
        pago.setIdPago(1L);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        doNothing().when(pagoRepository).delete(pago);

        pagoService.eliminarPago(1L);

        verify(pagoRepository, times(1)).delete(pago);
    }
}
