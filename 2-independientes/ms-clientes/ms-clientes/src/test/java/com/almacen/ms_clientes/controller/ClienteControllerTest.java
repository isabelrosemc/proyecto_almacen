package com.almacen.ms_clientes.controller;

import com.almacen.ms_clientes.dto.ClienteResponseDTO;
import com.almacen.ms_clientes.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Test
    void debeBuscarClientePorId() {
        // ARRANGE: preparar service simulado y controller real
        ClienteService service = mock(ClienteService.class);
        ClienteController controller = new ClienteController(service);

        ClienteResponseDTO respuestaMock = ClienteResponseDTO.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@email.com")
                .telefono("912345678")
                .direccion("Santiago")
                .estado(true)
                .fechaRegistro(LocalDateTime.now())
                .build();

        when(service.buscarPorId(1L)).thenReturn(respuestaMock);

        // ACT: ejecutar endpoint del controller
        ResponseEntity<ClienteResponseDTO> response = controller.buscarPorId(1L);

        // ASSERT: verificar resultado esperado
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Juan Perez", response.getBody().getNombreCompleto());
        assertEquals("juan@email.com", response.getBody().getEmail());

        // VERIFY: comprobar llamada al mock
        verify(service).buscarPorId(1L);

        // Caso hipotético de falla para QA:
        // Si el test falla, QA puede reportar que el endpoint buscarPorId
        // no retorna HTTP 200 o no devuelve los datos esperados del cliente.
    }
}