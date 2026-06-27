package com.almacen.ms_compras.service;

import com.almacen.ms_compras.client.ProveedorFeignClient;
import com.almacen.ms_compras.client.ProductoFeignClient;
import com.almacen.ms_compras.client.StockFeignClient;
import com.almacen.ms_compras.dto.*;
import com.almacen.ms_compras.exception.CompraNotFoundException;
import com.almacen.ms_compras.exception.RemoteServiceException;
import com.almacen.ms_compras.model.Compra;
import com.almacen.ms_compras.repository.CompraRepository;
import com.almacen.ms_compras.service.impl.CompraServiceImpl;
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
 * Test unitario para CompraServiceImpl.
 * CompraRepository, ProveedorFeignClient, ProductoFeignClient y StockFeignClient quedan simulados.
 */
@ExtendWith(MockitoExtension.class)
class CompraServiceImplTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private ProveedorFeignClient proveedorFeignClient;

    @Mock
    private ProductoFeignClient productoFeignClient;

    @Mock
    private StockFeignClient stockFeignClient;

    @InjectMocks
    private CompraServiceImpl compraService;

    // ──────────────────────────────────────────────────────────────────
    // crearCompra
    // ──────────────────────────────────────────────────────────────────

    @Test
    void crearCompra_cuandoProveedorNoExiste_deberiaLanzarExcepcion() {

        // ARRANGE: el Feign client lanza 404 al buscar el proveedor.
        CompraRequestDTO request = CompraRequestDTO.builder()
                .proveedorId(99L)
                .detalles(List.of(
                        DetalleCompraRequestDTO.builder()
                                .productoId(1L)
                                .cantidad(5)
                                .build()
                ))
                .build();

        when(proveedorFeignClient.obtenerProveedor(99L))
                .thenThrow(FeignException.NotFound.class);

        // ACT + ASSERT
        assertThatThrownBy(() -> compraService.crearCompra(request))
                .isInstanceOf(RemoteServiceException.class)
                .hasMessageContaining("Proveedor no encontrado");

        verify(compraRepository, never()).save(any());

        /*
         * CASO HIPOTÉTICO DE FALLA PARA QA:
         * Si el service no lanza RemoteServiceException cuando el proveedor no existe,
         * se podría crear una compra con proveedorId inexistente.
         * Desarrollo debe revisar el bloque try/catch en crearCompra().
         */
    }

    // ──────────────────────────────────────────────────────────────────
    // listarCompras
    // ──────────────────────────────────────────────────────────────────

    @Test
    void listarCompras_cuandoNoHayCompras_deberiaRetornarListaVacia() {

        // ARRANGE
        when(compraRepository.findAll()).thenReturn(List.of());

        // ACT
        List<CompraResponseDTO> resultado = compraService.listarCompras();

        // ASSERT
        assertThat(resultado).isEmpty();
        verify(compraRepository, times(1)).findAll();
    }

    // ──────────────────────────────────────────────────────────────────
    // buscarPorId
    // ──────────────────────────────────────────────────────────────────

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        // ARRANGE
        when(compraRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThatThrownBy(() -> compraService.buscarPorId(99L))
                .isInstanceOf(CompraNotFoundException.class);
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarCompraConDetalles() {

        // ARRANGE
        Compra compra = new Compra();
        compra.setId(1L);
        compra.setProveedorId(2L);
        compra.setTotal(new BigDecimal("150000"));
        compra.setEstado(true);
        compra.setDetalles(List.of());

        ProveedorDTO proveedorDTO = new ProveedorDTO(2L, "Distribuidora XYZ");

        when(compraRepository.findById(1L)).thenReturn(Optional.of(compra));
        when(proveedorFeignClient.obtenerProveedor(2L)).thenReturn(proveedorDTO);

        // ACT
        CompraResponseDTO resultado = compraService.buscarPorId(1L);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getProveedor().getRazonSocial()).isEqualTo("Distribuidora XYZ");
    }
}
