package com.almacen.ms_productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.almacen.ms_productos.model.Producto;
import com.almacen.ms_productos.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

}
