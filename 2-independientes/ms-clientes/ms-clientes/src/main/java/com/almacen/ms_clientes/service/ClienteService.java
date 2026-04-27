package com.almacen.ms_clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.ms_clientes.model.Cliente;
import com.almacen.ms_clientes.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    public ClienteRepository clienteRepository;

    //metodo crear--------------------------------------
    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    //metodo listar------------------------------------------------------------
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    //metodo buscar por id------------------
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el cliente con el ID: " + id)); 
    }

    //metodo buscar por apodo--------------------------------------
    public Cliente obtenerPorApodo(String apodo) {
        return clienteRepository.findByApodoIgnoreCase(apodo)
            .orElseThrow(() -> new RuntimeException("No se encontró ningún cliente con el apodo: " + apodo));
    }

    //metodo actualizar datos del cliente (deuda, limite para fiar, etc)--------------------------------
    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        Cliente existe = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el cliente para actualizar"));
        
        existe.setNombre(clienteActualizado.getNombre());
        existe.setApodo(clienteActualizado.getApodo());
        existe.setTelefono(clienteActualizado.getTelefono());
        existe.setDeudaActual(clienteActualizado.getDeudaActual());
        existe.setLimiteCredito(clienteActualizado.getLimiteCredito());

        return clienteRepository.save(existe);
    }

    //metodo eliminar-----------------------------------------
    public void eliminar(Long id) {
        Cliente existe = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el cliente para eliminar"));
        clienteRepository.delete(existe);
    }




}
