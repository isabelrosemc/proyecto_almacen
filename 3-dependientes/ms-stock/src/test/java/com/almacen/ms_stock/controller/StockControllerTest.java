package com.almacen.ms_stock.controller;

import com.almacen.ms_stock.dto.*;
import com.almacen.ms_stock.service.StockService;
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
 * Test unitario para StockController.
 * StockService queda simulado con Mockito.
 */
@WebMvcTest(StockController.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StockService stockService;

    @Test
    void listarStock_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        StockResponseDTO dto = StockResponseDTO.builder()
                .id(1L)
                .stockActual(100)
                .stockMinimo(10)
                .stockMaximo(500)
                .estado(true)
                .build();

        when(stockService.listarStock()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].stockActual").value(100));

        verify(stockService, times(1)).listarStock();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        StockResponseDTO dto = StockResponseDTO.builder()
                .id(1L)
                .stockActual(100)
                .build();

        when(stockService.buscarPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/stock/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void crearStock_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        StockRequestDTO request = StockRequestDTO.builder()
                .productoId(1L)
                .stockActual(50)
                .stockMinimo(5)
                .stockMaximo(200)
                .estado(true)
                .build();

        StockResponseDTO response = StockResponseDTO.builder()
                .id(1L)
                .stockActual(50)
                .mensaje("Stock creado correctamente")
                .build();

        when(stockService.crearStock(any(StockRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 201 Created.
         * Se obtuvo 200 OK.
         * Causa: el controller usa ResponseEntity.ok() en POST.
         */
    }

    @Test
    void ingresarStock_conDatosValidos_deberiaRetornar200() throws Exception {

        // ARRANGE
        ActualizarStockDTO request = new ActualizarStockDTO();
        request.setProductoId(1L);
        request.setCantidad(30);

        doNothing().when(stockService).ingresarStock(any(ActualizarStockDTO.class));

        // ACT + ASSERT
        mockMvc.perform(put("/api/stock/ingresar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(stockService, times(1)).ingresarStock(any(ActualizarStockDTO.class));
    }

    @Test
    void eliminarStock_cuandoExiste_deberiaRetornar204() throws Exception {

        doNothing().when(stockService).eliminarStock(1L);

        mockMvc.perform(delete("/api/stock/1"))
                .andExpect(status().isNoContent());

        verify(stockService, times(1)).eliminarStock(1L);
    }
}
