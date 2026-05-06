package com.almacen.ms_clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almacen.ms_clientes.model.Cliente;
import com.almacen.ms_clientes.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Crear
    @PostMapping
    public Cliente crear(@Valid @RequestBody Cliente cliente){
        return clienteService.crear(cliente);
    }

    // Listar todos
    @GetMapping
    public List<Cliente> listar(){
        return clienteService.listar();
    }

    // Obtener por id
    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable Long id){
        return clienteService.obtenerPorId(id);
    }

    // Actualizar
    @PutMapping("/{id}")
    public Cliente actualizar( @PathVariable Long id, @Valid @RequestBody Cliente cliente){
        return clienteService.actualizar(id, cliente);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
    clienteService.eliminar(id);
    }

    // Metodo custom (buscar por apodo)
    @GetMapping("/apodo/{apodo}")
    public Cliente buscarPorApodo(@PathVariable String apodo){
        return clienteService.obtenerPorApodo(apodo);
    }
}
