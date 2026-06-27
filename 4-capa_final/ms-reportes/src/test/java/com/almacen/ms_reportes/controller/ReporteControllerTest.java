package com.almacen.ms_reportes.controller;

import com.almacen.ms_reportes.dto.VentaResponseDTO;
import com.almacen.ms_reportes.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test unitario para ReporteController.
 * ReporteService queda simulado con Mockito.
 * No se levanta el servidor real ni ms-ventas.
 */
@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReporteService reporteService;

    @Test
    void obtenerReporteVentas_deberiaRetornar200YLista() throws Exception {

        // ARRANGE: el service simulado devuelve una lista con dos ventas.
        VentaResponseDTO venta1 = VentaResponseDTO.builder()
                .id(1L)
                .total(new BigDecimal("29990"))
                .estado(true)
                .build();

        VentaResponseDTO venta2 = VentaResponseDTO.builder()
                .id(2L)
                .total(new BigDecimal("59980"))
                .estado(true)
                .build();

        when(reporteService.obtenerReporteVentas()).thenReturn(List.of(venta1, venta2));

        // ACT + ASSERT: GET /api/reportes/ventas debe responder 200 OK con 2 elementos.
        mockMvc.perform(get("/api/reportes/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].total").value(29990))
                .andExpect(jsonPath("$[1].id").value(2L));

        // VERIFY: el service fue invocado una vez.
        verify(reporteService, times(1)).obtenerReporteVentas();

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 200 OK con lista de ventas.
         * Se obtuvo 500 Internal Server Error.
         * Posible causa: ms-ventas no responde y VentasClient lanza excepción sin manejo.
         * Desarrollo debe agregar manejo de errores en ReporteServiceImpl
         * o un fallback en VentasClient.
         */
    }

    @Test
    void obtenerReporteVentas_cuandoNoHayVentas_deberiaRetornar200YListaVacia() throws Exception {

        // ARRANGE
        when(reporteService.obtenerReporteVentas()).thenReturn(List.of());

        // ACT + ASSERT
        mockMvc.perform(get("/api/reportes/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(reporteService, times(1)).obtenerReporteVentas();
    }
}
