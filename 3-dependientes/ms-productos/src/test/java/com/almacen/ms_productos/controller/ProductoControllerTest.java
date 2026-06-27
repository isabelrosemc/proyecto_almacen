package com.almacen.ms_productos.controller;

import com.almacen.ms_productos.dto.*;
import com.almacen.ms_productos.service.ProductoService;
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
 * Test unitario para ProductoController.
 * ProductoService queda simulado con Mockito.
 */
@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void listarProductos_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        ProductoResponseDTO dto = ProductoResponseDTO.builder()
                .id(1L)
                .nombre("Laptop HP")
                .sku("SKU-001")
                .precio(new BigDecimal("599990"))
                .estado(true)
                .build();

        when(productoService.listarProductos()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sku").value("SKU-001"));

        verify(productoService, times(1)).listarProductos();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        ProductoResponseDTO dto = ProductoResponseDTO.builder()
                .id(1L)
                .nombre("Laptop HP")
                .sku("SKU-001")
                .build();

        when(productoService.buscarPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.sku").value("SKU-001"));
    }

    @Test
    void crearProducto_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        ProductoRequestDTO request = ProductoRequestDTO.builder()
                .nombre("Monitor LG")
                .descripcion("Monitor 24 pulgadas Full HD")
                .sku("SKU-002")
                .precio(new BigDecimal("199990"))
                .categoriaId(1L)
                .proveedorId(2L)
                .estado(true)
                .build();

        ProductoResponseDTO response = ProductoResponseDTO.builder()
                .id(2L)
                .nombre("Monitor LG")
                .sku("SKU-002")
                .build();

        when(productoService.crearProducto(any(ProductoRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 201 Created.
         * Se obtuvo 200 OK.
         * Causa: falta ResponseEntity.status(HttpStatus.CREATED) en el controller.
         */
    }

    @Test
    void eliminarProducto_cuandoExiste_deberiaRetornar204() throws Exception {

        doNothing().when(productoService).eliminarProducto(1L);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());

        verify(productoService, times(1)).eliminarProducto(1L);
    }
}
