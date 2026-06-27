package com.almacen.ms_pagos.controller;

import com.almacen.ms_pagos.dto.PagoDTO;
import com.almacen.ms_pagos.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test unitario para PagoController.
 * PagoService queda simulado con Mockito.
 */
@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PagoService service;

    @Test
    void listarPagos_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        PagoDTO dto = new PagoDTO();
        dto.setMonto(100000.0);
        dto.setMetodoPago("EFECTIVO");
        dto.setEstadoPago("APROBADO");
        dto.setIdVenta(1L);

        when(service.listarPagos()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].metodoPago").value("EFECTIVO"));

        verify(service, times(1)).listarPagos();
    }

    @Test
    void buscarPago_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        PagoDTO dto = new PagoDTO();
        dto.setMonto(50000.0);
        dto.setIdVenta(2L);

        when(service.buscarPago(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVenta").value(2L));
    }

    @Test
    void guardarPago_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        PagoDTO request = new PagoDTO();
        request.setMonto(250000.0);
        request.setMetodoPago("TARJETA");
        request.setEstadoPago("APROBADO");
        request.setIdVenta(3L);

        PagoDTO response = new PagoDTO();
        response.setMonto(250000.0);
        response.setIdVenta(3L);

        when(service.guardarPago(any(PagoDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idVenta").value(3L));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 201 Created.
         * Se obtuvo 200 OK.
         */
    }

    @Test
    void eliminarPago_cuandoExiste_deberiaRetornar200() throws Exception {

        doNothing().when(service).eliminarPago(1L);

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).eliminarPago(1L);
    }
}
