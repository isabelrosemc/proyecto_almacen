package com.almacen.ms_proveedores.controller;

import com.almacen.ms_proveedores.dto.ProveedorRequestDTO;
import com.almacen.ms_proveedores.dto.ProveedorResponseDTO;
import com.almacen.ms_proveedores.service.ProveedorService;
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
 * Test unitario para ProveedorController.
 * ProveedorService queda simulado con Mockito.
 */
@WebMvcTest(ProveedorController.class)
class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProveedorService proveedorService;

    @Test
    void listarProveedores_deberiaRetornar200YLista() throws Exception {

        // ARRANGE
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(1L)
                .razonSocial("Distribuidora XYZ")
                .rut("76543210-9")
                .email("contacto@xyz.cl")
                .build();

        when(proveedorService.listarProveedores()).thenReturn(List.of(dto));

        // ACT + ASSERT
        mockMvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].rut").value("76543210-9"));

        verify(proveedorService, times(1)).listarProveedores();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornar200() throws Exception {

        // ARRANGE
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(1L)
                .razonSocial("Distribuidora XYZ")
                .rut("76543210-9")
                .build();

        when(proveedorService.buscarPorId(1L)).thenReturn(dto);

        // ACT + ASSERT
        mockMvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(proveedorService, times(1)).buscarPorId(1L);
    }

    @Test
    void crearProveedor_conDatosValidos_deberiaRetornar201() throws Exception {

        // ARRANGE
        ProveedorRequestDTO request = ProveedorRequestDTO.builder()
                .razonSocial("Nuevo Proveedor SA")
                .rut("12345678-9")
                .email("nuevo@proveedor.cl")
                .telefono("22222222")
                .direccion("Calle Nueva 1")
                .estado(true)
                .build();

        ProveedorResponseDTO response = ProveedorResponseDTO.builder()
                .id(3L)
                .razonSocial("Nuevo Proveedor SA")
                .rut("12345678-9")
                .build();

        when(proveedorService.crearProveedor(any(ProveedorRequestDTO.class))).thenReturn(response);

        // ACT + ASSERT
        mockMvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L));

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Se esperaba 201 Created, se obtuvo 200 OK.
         * Causa: el controller usa ResponseEntity.ok() en lugar de
         * ResponseEntity.status(HttpStatus.CREATED).
         */
    }

    @Test
    void eliminarProveedor_cuandoExiste_deberiaRetornar204() throws Exception {

        doNothing().when(proveedorService).eliminarProveedor(1L);

        mockMvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isNoContent());

        verify(proveedorService, times(1)).eliminarProveedor(1L);
    }
}
