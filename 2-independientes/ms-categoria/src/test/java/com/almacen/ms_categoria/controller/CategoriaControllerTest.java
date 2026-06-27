package com.almacen.ms_categoria.controller;

import com.almacen.ms_categoria.dto.CategoriaRequestDTO;
import com.almacen.ms_categoria.dto.CategoriaResponseDTO;
import com.almacen.ms_categoria.service.CategoriaService;
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
 * Test unitario para CategoriaController.
 *
 * No se levanta el servidor real en ningún puerto.
 * No se usa MySQL ni Eureka.
 * MockMvc simula las peticiones HTTP REST.
 * CategoriaService queda simulado con Mockito.
 */
@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaService categoriaService;

    // ──────────────────────────────────────────────────────────────────
    // GET /api/categorias
    // ──────────────────────────────────────────────────────────────────

    @Test
    void listarCategorias_deberiaRetornar200YLista() throws Exception {

        // ARRANGE: el service simulado devuelve una lista con un elemento.
        CategoriaResponseDTO dto = CategoriaResponseDTO.builder()
                .id(1L)
                .nombre("Electrónica")
                .descripcion("Productos electrónicos")
                .estado(true)
                .build();

        when(categoriaService.listarCategorias()).thenReturn(List.of(dto));

        // ACT + ASSERT: GET /api/categorias debe responder 200 OK con un arreglo JSON de 1 elemento.
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Electrónica"));

        // VERIFY: el service fue invocado una vez.
        verify(categoriaService, times(1)).listarCategorias();

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Si el endpoint GET /api/categorias responde 500 en vez de 200,
         * revisar que CategoriaService no lanza excepción inesperada.
         */
    }

    // ──────────────────────────────────────────────────────────────────
    // GET /api/categorias/{id}
    // ──────────────────────────────────────────────────────────────────

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornar200YCategoria() throws Exception {

        // ARRANGE
        CategoriaResponseDTO dto = CategoriaResponseDTO.builder()
                .id(1L)
                .nombre("Ropa")
                .descripcion("Prendas de vestir")
                .estado(true)
                .build();

        when(categoriaService.buscarPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Ropa"))
                .andExpect(jsonPath("$.descripcion").value("Prendas de vestir"));

        verify(categoriaService, times(1)).buscarPorId(1L);
    }

    // ──────────────────────────────────────────────────────────────────
    // POST /api/categorias
    // ──────────────────────────────────────────────────────────────────

    @Test
    void crearCategoria_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        CategoriaRequestDTO request = CategoriaRequestDTO.builder()
                .nombre("Alimentos")
                .descripcion("Productos de alimentación")
                .estado(true)
                .build();

        CategoriaResponseDTO response = CategoriaResponseDTO.builder()
                .id(2L)
                .nombre("Alimentos")
                .descripcion("Productos de alimentación")
                .estado(true)
                .build();

        when(categoriaService.crearCategoria(any(CategoriaRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT: POST /api/categorias debe responder 201 Created.
        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.nombre").value("Alimentos"));

        verify(categoriaService, times(1)).crearCategoria(any(CategoriaRequestDTO.class));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba: 201 Created.
         * Se obtuvo: 200 OK.
         * Posible causa: el controller usa ResponseEntity.ok() en vez de
         * ResponseEntity.status(HttpStatus.CREATED).
         */
    }

    // ──────────────────────────────────────────────────────────────────
    // DELETE /api/categorias/{id}
    // ──────────────────────────────────────────────────────────────────

    @Test
    void eliminarCategoria_cuandoExiste_deberiaRetornar204() throws Exception {

        // ARRANGE
        doNothing().when(categoriaService).eliminarCategoria(1L);

        // ACT + ASSERT: DELETE /api/categorias/1 debe responder 204 No Content.
        mockMvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).eliminarCategoria(1L);
    }
}
