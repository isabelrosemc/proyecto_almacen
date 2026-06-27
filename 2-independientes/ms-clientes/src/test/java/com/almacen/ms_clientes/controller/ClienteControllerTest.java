package com.almacen.ms_clientes.controller;

import com.almacen.ms_clientes.dto.ClienteRequestDTO;
import com.almacen.ms_clientes.dto.ClienteResponseDTO;
import com.almacen.ms_clientes.service.ClienteService;
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
 * Test unitario para ClienteController.
 * MockMvc simula peticiones HTTP REST.
 * ClienteService queda simulado con Mockito.
 */
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService service;

    @Test
    void listar_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        ClienteResponseDTO dto = ClienteResponseDTO.builder()
                .id(1L)
                .nombreCompleto("Ana García")
                .email("ana@correo.cl")
                .estado(true)
                .build();

        when(service.listar()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("ana@correo.cl"));

        verify(service, times(1)).listar();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        ClienteResponseDTO dto = ClienteResponseDTO.builder()
                .id(1L)
                .nombreCompleto("Ana García")
                .email("ana@correo.cl")
                .estado(true)
                .build();

        when(service.buscarPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombreCompleto").value("Ana García"));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void crear_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setNombre("Pedro");
        request.setApellido("Soto");
        request.setEmail("pedro@correo.cl");
        request.setTelefono("912345678");

        ClienteResponseDTO response = ClienteResponseDTO.builder()
                .id(2L)
                .nombreCompleto("Pedro Soto")
                .email("pedro@correo.cl")
                .estado(true)
                .build();

        when(service.crear(any(ClienteRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L));

        verify(service, times(1)).crear(any(ClienteRequestDTO.class));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba: 201 Created.
         * Se obtuvo: 200 OK.
         * Desarrollo debe verificar que el endpoint POST retorna
         * ResponseEntity.status(HttpStatus.CREATED).
         */
    }

    @Test
    void eliminar_cuandoExiste_deberiaRetornar204() throws Exception {

        // ARRANGE
        doNothing().when(service).eliminar(1L);

        // ACT + ASSERT
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).eliminar(1L);
    }
}
