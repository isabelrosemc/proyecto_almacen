package com.almacen.ms_productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.almacen.ms_productos.model.Producto;
import com.almacen.ms_productos.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto crear(Producto producto){
        // Validaciones
        return productoRepository.save(producto);
    }

    // CRUD: Listar todos los productos
    public List<Producto> listar(){             // Lista todo
        return productoRepository.findAll();
    }

    // CRUD: Buscar por Id (por id)
    public Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe producto con tal id"));
    }

    // CRUD: Actualizar datos
    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto existe = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe producto con tal id"));

        // actualizas solo los campos necesarios
        existe.setNombre(productoActualizado.getNombre());
        existe.setMarca(productoActualizado.getMarca());
        existe.setDescripcion(productoActualizado.getDescripcion());
        existe.setIdCategoria(productoActualizado.getIdCategoria());
        existe.setPrecioVenta(productoActualizado.getPrecioVenta());
        existe.setUnidadMedida(productoActualizado.getUnidadMedida());
        existe.setContenidoNeto(productoActualizado.getContenidoNeto());

        return productoRepository.save(existe);
    }

    // CRUD: Eliminar
    public void eliminar(Long id) {
    Producto existe = productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No existe producto con tal id"));

    productoRepository.delete(existe);
    }
}
