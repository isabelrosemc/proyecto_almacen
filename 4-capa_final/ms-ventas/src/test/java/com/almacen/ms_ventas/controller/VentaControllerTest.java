package com.almacen.ms_ventas.controller;

import com.almacen.ms_ventas.dto.*;
import com.almacen.ms_ventas.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test unitario para VentaController.
 * VentaService queda simulado con Mockito.
 */
@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VentaService ventaService;

    @Test
    void listarVentas_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        ClienteDTO cliente = new ClienteDTO(1L, "Ana García", "ana@correo.cl");

        VentaResponseDTO dto = VentaResponseDTO.builder()
                .id(1L)
                .cliente(cliente)
                .total(new BigDecimal("29990"))
                .estado(true)
                .build();

        when(ventaService.listarVentas()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].total").value(29990));

        verify(ventaService, times(1)).listarVentas();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        ClienteDTO cliente = new ClienteDTO(1L, "Ana García", "ana@correo.cl");

        VentaResponseDTO dto = VentaResponseDTO.builder()
                .id(1L)
                .cliente(cliente)
                .total(new BigDecimal("29990"))
                .build();

        when(ventaService.buscarPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cliente.email").value("ana@correo.cl"));

        verify(ventaService, times(1)).buscarPorId(1L);
    }

    @Test
    void crearVenta_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        DetalleVentaRequestDTO detalle = DetalleVentaRequestDTO.builder()
                .productoId(1L)
                .cantidad(2)
                .build();

        VentaRequestDTO request = VentaRequestDTO.builder()
                .clienteId(1L)
                .detalles(List.of(detalle))
                .build();

        ClienteDTO cliente = new ClienteDTO(1L, "Ana García", "ana@correo.cl");

        VentaResponseDTO response = VentaResponseDTO.builder()
                .id(1L)
                .cliente(cliente)
                .total(new BigDecimal("59980"))
                .estado(true)
                .build();

        when(ventaService.crearVenta(any(VentaRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.total").value(59980));

        verify(ventaService, times(1)).crearVenta(any(VentaRequestDTO.class));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 201 Created.
         * Se obtuvo 400 Bad Request.
         * Posible causa: los detalles de la venta no pasan la validación @Valid.
         * Desarrollo debe revisar DetalleVentaRequestDTO y que @Valid esté en el @RequestBody.
         */
    }
}
