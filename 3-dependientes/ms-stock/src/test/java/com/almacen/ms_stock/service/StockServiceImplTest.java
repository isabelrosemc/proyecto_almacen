package com.almacen.ms_stock.service;

import com.almacen.ms_stock.client.ProductoFeignClient;
import com.almacen.ms_stock.dto.*;
import com.almacen.ms_stock.exception.*;
import com.almacen.ms_stock.mapper.StockMapper;
import com.almacen.ms_stock.model.Stock;
import com.almacen.ms_stock.repository.StockRepository;
import com.almacen.ms_stock.service.impl.StockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario para StockServiceImpl.
 * StockRepository y ProductoFeignClient quedan simulados.
 */
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductoFeignClient productoFeignClient;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void crearStock_cuandoDatosValidos_deberiaGuardarYRetornarDTO() {

        // ARRANGE
        StockRequestDTO request = StockRequestDTO.builder()
                .productoId(1L)
                .stockActual(50)
                .stockMinimo(5)
                .stockMaximo(100)
                .estado(true)
                .build();

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);

        Stock entidad = new Stock();
        entidad.setId(1L);
        entidad.setProductoId(1L);
        entidad.setStockActual(50);
        entidad.setStockMinimo(5);
        entidad.setStockMaximo(100);

        StockResponseDTO expected = StockResponseDTO.builder()
                .id(1L)
                .stockActual(50)
                .build();

        when(stockRepository.existsByProductoId(1L)).thenReturn(false);
        when(productoFeignClient.obtenerProducto(1L)).thenReturn(producto);
        when(stockRepository.save(any(Stock.class))).thenReturn(entidad);

        try (MockedStatic<StockMapper> mapper = mockStatic(StockMapper.class)) {
            mapper.when(() -> StockMapper.toEntity(request)).thenReturn(entidad);
            mapper.when(() -> StockMapper.toDTO(entidad, producto)).thenReturn(expected);

            // ACT
            StockResponseDTO resultado = stockService.crearStock(request);

            // ASSERT
            assertThat(resultado.getStockActual()).isEqualTo(50);
        }

        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void crearStock_cuandoProductoYaTieneStock_deberiaLanzarExcepcion() {

        // ARRANGE
        StockRequestDTO request = StockRequestDTO.builder()
                .productoId(1L)
                .stockActual(10)
                .stockMinimo(2)
                .stockMaximo(50)
                .build();

        when(stockRepository.existsByProductoId(1L)).thenReturn(true);

        // ACT + ASSERT
        assertThatThrownBy(() -> stockService.crearStock(request))
                .isInstanceOf(DuplicateStockException.class);

        verify(stockRepository, never()).save(any());
    }

    @Test
    void crearStock_cuandoStockActualNegativo_deberiaLanzarExcepcion() {

        // ARRANGE: stock actual negativo viola la regla de negocio.
        StockRequestDTO request = StockRequestDTO.builder()
                .productoId(2L)
                .stockActual(-1)
                .stockMinimo(0)
                .stockMaximo(100)
                .build();

        when(stockRepository.existsByProductoId(2L)).thenReturn(false);

        // ACT + ASSERT
        assertThatThrownBy(() -> stockService.crearStock(request))
                .isInstanceOf(InvalidStockException.class)
                .hasMessageContaining("negativo");
    }

    @Test
    void ingresarStock_cuandoCantidadValida_deberiaActualizarStockActual() {

        // ARRANGE
        Stock stock = new Stock();
        stock.setProductoId(1L);
        stock.setStockActual(50);
        stock.setStockMinimo(5);
        stock.setStockMaximo(100);

        ActualizarStockDTO request = new ActualizarStockDTO();
        request.setProductoId(1L);
        request.setCantidad(20);

        when(stockRepository.findByProductoId(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(stock)).thenReturn(stock);

        // ACT
        stockService.ingresarStock(request);

        // ASSERT: el stock actual debe haber aumentado a 70.
        assertThat(stock.getStockActual()).isEqualTo(70);
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void descontarStock_cuandoStockInsuficiente_deberiaLanzarExcepcion() {

        // ARRANGE
        Stock stock = new Stock();
        stock.setProductoId(1L);
        stock.setStockActual(5);

        ActualizarStockDTO request = new ActualizarStockDTO();
        request.setProductoId(1L);
        request.setCantidad(10); // pide más de lo disponible

        when(stockRepository.findByProductoId(1L)).thenReturn(Optional.of(stock));

        // ACT + ASSERT
        assertThatThrownBy(() -> stockService.descontarStock(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("insuficiente");
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {

        when(stockRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> stockService.buscarPorId(99L))
                .isInstanceOf(StockNotFoundException.class);
    }
}
