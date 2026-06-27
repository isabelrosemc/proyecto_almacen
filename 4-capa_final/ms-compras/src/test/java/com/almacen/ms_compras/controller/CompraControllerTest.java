package com.almacen.ms_compras.controller;

import com.almacen.ms_compras.dto.*;
import com.almacen.ms_compras.service.CompraService;
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
 * Test unitario para CompraController.
 * CompraService queda simulado con Mockito.
 */
@WebMvcTest(CompraController.class)
class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CompraService compraService;

    @Test
    void listarCompras_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        ProveedorDTO proveedor = new ProveedorDTO(2L, "Distribuidora XYZ");

        CompraResponseDTO dto = CompraResponseDTO.builder()
                .id(1L)
                .proveedor(proveedor)
                .total(new BigDecimal("150000"))
                .estado(true)
                .build();

        when(compraService.listarCompras()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/compras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].total").value(150000));

        verify(compraService, times(1)).listarCompras();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        ProveedorDTO proveedor = new ProveedorDTO(2L, "Distribuidora XYZ");

        CompraResponseDTO dto = CompraResponseDTO.builder()
                .id(1L)
                .proveedor(proveedor)
                .total(new BigDecimal("150000"))
                .build();

        when(compraService.buscarPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/compras/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.proveedor.razonSocial").value("Distribuidora XYZ"));

        verify(compraService, times(1)).buscarPorId(1L);
    }

    @Test
    void crearCompra_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        DetalleCompraRequestDTO detalle = DetalleCompraRequestDTO.builder()
                .productoId(1L)
                .cantidad(10)
                .build();

        CompraRequestDTO request = CompraRequestDTO.builder()
                .proveedorId(2L)
                .detalles(List.of(detalle))
                .build();

        ProveedorDTO proveedor = new ProveedorDTO(2L, "Distribuidora XYZ");

        CompraResponseDTO response = CompraResponseDTO.builder()
                .id(1L)
                .proveedor(proveedor)
                .total(new BigDecimal("500000"))
                .estado(true)
                .build();

        when(compraService.crearCompra(any(CompraRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.total").value(500000));

        verify(compraService, times(1)).crearCompra(any(CompraRequestDTO.class));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 201 Created.
         * Se obtuvo 400 Bad Request.
         * Posible causa: los detalles no pasan validación @Valid.
         * Desarrollo debe revisar DetalleCompraRequestDTO y @Valid en @RequestBody.
         */
    }
}
