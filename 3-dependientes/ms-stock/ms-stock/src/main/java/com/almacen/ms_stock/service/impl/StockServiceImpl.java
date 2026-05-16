package com.almacen.ms_stock.service.impl;

import com.almacen.ms_stock.client.ProductoFeignClient;
import com.almacen.ms_stock.dto.*;
import com.almacen.ms_stock.exception.*;
import com.almacen.ms_stock.mapper.StockMapper;
import com.almacen.ms_stock.model.Stock;
import com.almacen.ms_stock.repository.StockRepository;
import com.almacen.ms_stock.service.StockService;

import feign.FeignException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl
        implements StockService {

    private final StockRepository stockRepository;

    private final ProductoFeignClient productoFeignClient;

    @Override
    public StockResponseDTO crearStock(
            StockRequestDTO request
    ) {

        log.info("Creando stock producto ID: {}",
                request.getProductoId());

        boolean existe =
                stockRepository.existsByProductoId(
                        request.getProductoId()
                );

        if (existe) {

            throw new DuplicateStockException(
                    "El producto ya tiene stock"
            );
        }

        validarReglasStock(request);

        ProductoDTO producto;

        try {

            producto =
                    productoFeignClient.obtenerProducto(
                            request.getProductoId()
                    );

        } catch (FeignException.NotFound ex) {

            throw new RemoteServiceException(
                    "Producto no encontrado"
            );
        }

        Stock stock =
                StockMapper.toEntity(request);

        Stock guardado =
                stockRepository.save(stock);

        log.info("Stock creado correctamente ID: {}",
                guardado.getId());

        StockResponseDTO response =
                StockMapper.toDTO(
                        guardado,
                        producto
                );

        response.setMensaje(
                "Stock creado correctamente"
        );

        return response;
    }

    @Override
    public List<StockResponseDTO> listarStock() {

        log.info("Listando stock");

        return stockRepository.findAll()
                .stream()
                .map(stock -> {

                    ProductoDTO producto =
                            productoFeignClient
                                    .obtenerProducto(
                                            stock.getProductoId()
                                    );

                    return StockMapper.toDTO(
                            stock,
                            producto
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public StockResponseDTO buscarPorId(Long id) {

        log.info("Buscando stock ID: {}", id);

        Stock stock =
                stockRepository.findById(id)
                        .orElseThrow(() ->
                                new StockNotFoundException(
                                        "Stock no encontrado"
                                ));

        ProductoDTO producto =
                productoFeignClient.obtenerProducto(
                        stock.getProductoId()
                );

        return StockMapper.toDTO(
                stock,
                producto
        );
    }

    @Override
    public StockResponseDTO actualizarStock(
            Long id,
            StockRequestDTO request
    ) {

        log.info("Actualizando stock ID: {}", id);

        validarReglasStock(request);

        Stock stock =
                stockRepository.findById(id)
                        .orElseThrow(() ->
                                new StockNotFoundException(
                                        "Stock no encontrado"
                                ));

        stock.setProductoId(request.getProductoId());
        stock.setStockActual(request.getStockActual());
        stock.setStockMinimo(request.getStockMinimo());
        stock.setStockMaximo(request.getStockMaximo());
        if (request.getEstado() != null) {

                stock.setEstado(request.getEstado());
}
        Stock actualizado =
                stockRepository.save(stock);

        ProductoDTO producto =
                productoFeignClient.obtenerProducto(
                        actualizado.getProductoId()
                );

        String mensaje =
                "Stock actualizado correctamente";

        if (actualizado.getStockActual() == 0) {

            mensaje = "SIN STOCK";
        }
        else if (
                actualizado.getStockActual()
                <= actualizado.getStockMinimo()
        ) {

            mensaje =
                    "ALERTA: stock bajo minimo";
        }

        log.info("Stock actualizado correctamente");

        StockResponseDTO response =
                StockMapper.toDTO(
                        actualizado,
                        producto
                );

        response.setMensaje(mensaje);

        return response;
    }

    @Override
    public void eliminarStock(Long id) {

        log.info("Eliminando stock ID: {}", id);

        Stock stock =
                stockRepository.findById(id)
                        .orElseThrow(() ->
                                new StockNotFoundException(
                                        "Stock no encontrado"
                                ));

        stockRepository.delete(stock);

        log.info("Stock eliminado correctamente");
    }

    private void validarReglasStock(
            StockRequestDTO request
    ) {

        if (request.getStockActual() < 0) {

            throw new InvalidStockException(
                    "El stock no puede ser negativo"
            );
        }

        if (request.getStockMinimo()
                > request.getStockMaximo()) {

            throw new InvalidStockException(
                    "Stock minimo invalido"
            );
        }

        if (request.getStockActual()
                > request.getStockMaximo()) {

            throw new InvalidStockException(
                    "El stock supera el maximo"
            );
        }
    }
}