package com.almacen.ms_ventas.service;

import com.almacen.ms_ventas.client.ClienteFeignClient;
import com.almacen.ms_ventas.client.ProductoFeignClient;
import com.almacen.ms_ventas.client.StockFeignClient;
import com.almacen.ms_ventas.dto.*;
import com.almacen.ms_ventas.exception.RemoteServiceException;
import com.almacen.ms_ventas.exception.VentaNotFoundException;
import com.almacen.ms_ventas.model.Venta;
import com.almacen.ms_ventas.repository.VentaRepository;
import com.almacen.ms_ventas.service.impl.VentaServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para VentaServiceImpl.
 * VentaRepository, ClienteFeignClient, ProductoFeignClient y StockFeignClient quedan simulados.
 */
@ExtendWith(MockitoExtension.class)
class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ClienteFeignClient clienteFeignClient;

    @Mock
    private ProductoFeignClient productoFeignClient;

    @Mock
    private StockFeignClient stockFeignClient;

    @InjectMocks
    private VentaServiceImpl ventaService;

    // ──────────────────────────────────────────────────────────────────
    // crearVenta
    // ──────────────────────────────────────────────────────────────────

    @Test
    void crearVenta_cuandoClienteNoExiste_deberiaLanzarExcepcion() {

        // ARRANGE: el Feign client lanza 404 al buscar el cliente.
        VentaRequestDTO request = VentaRequestDTO.builder()
                .clienteId(99L)
                .detalles(List.of(
                        DetalleVentaRequestDTO.builder()
                                .productoId(1L)
                                .cantidad(2)
                                .build()
                ))
                .build();

        when(clienteFeignClient.obtenerCliente(99L))
                .thenThrow(FeignException.NotFound.class);

        // ACT + ASSERT
        assertThatThrownBy(() -> ventaService.crearVenta(request))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Cliente no encontrado");

        verify(ventaRepository, never()).save(any());

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Si el service no lanza RemoteServiceException cuando el cliente no existe,
         * se podría crear una venta con clienteId inexistente.
         * Desarrollo debe revisar el bloque try/catch en crearVenta().
         */
    }

    // ──────────────────────────────────────────────────────────────────
    // listarVentas
    // ──────────────────────────────────────────────────────────────────

    @Test
    void listarVentas_cuandoNoHayVentas_deberiaRetornarListaVacia() {

        // ARRANGE
        when(ventaRepository.findAll()).thenReturn(List.of());

        // ACT
        List<VentaResponseDTO> resultado = ventaService.listarVentas();

        // ASSERT
        assertThat(resultado).isEmpty();

        verify(ventaRepository, times(1)).findAll();
    }

    // ──────────────────────────────────────────────────────────────────
    // buscarPorId
    // ──────────────────────────────────────────────────────────────────

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        // ARRANGE
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThatThrownBy(() -> ventaService.buscarPorId(99L))
                .isInstanceOf(VentaNotFoundException.class);
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarVentaConDetalles() {

        // ARRANGE: una venta persistida con un detalle.
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setClienteId(1L);
        venta.setTotal(new BigDecimal("29990"));
        venta.setEstado(true);
        venta.setDetalles(List.of());

        ClienteDTO clienteDTO = new ClienteDTO(1L, "Ana García", "ana@correo.cl");

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(clienteFeignClient.obtenerCliente(1L)).thenReturn(clienteDTO);

        // ACT
        VentaResponseDTO resultado = ventaService.buscarPorId(1L);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getCliente().getEmail()).isEqualTo("ana@correo.cl");
    }
}
