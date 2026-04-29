package com.almacen.ms_clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almacen.ms_clientes.model.Cliente;
import com.almacen.ms_clientes.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // CREAR
    public Cliente crear(Cliente cliente) {

        // validar apodo duplicado
        if (clienteRepository.findByApodoIgnoreCase(cliente.getApodo()).isPresent()) {
            throw new RuntimeException("Ya existe un cliente con ese apodo");
        }

        // validar deuda vs limite
        if (cliente.getDeudaActual() > cliente.getLimiteCredito()) {
            throw new RuntimeException("La deuda no puede ser mayor al límite de crédito");
        }

        return clienteRepository.save(cliente);
    }

    // LISTAR
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    // OBTENER POR ID
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el cliente con el ID: " + id));
    }

    // OBTENER POR APODO
    public Cliente obtenerPorApodo(String apodo) {
        return clienteRepository.findByApodoIgnoreCase(apodo)
            .orElseThrow(() -> new RuntimeException("No se encontró ningún cliente con el apodo: " + apodo));
    }

    // ACTUALIZAR
    public Cliente actualizar(Long id, Cliente clienteActualizado) {

        Cliente existe = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el cliente para actualizar"));

        // validar cambio de apodo
        if (!existe.getApodo().equalsIgnoreCase(clienteActualizado.getApodo())) {
            if (clienteRepository.findByApodoIgnoreCase(clienteActualizado.getApodo()).isPresent()) {
                throw new RuntimeException("El apodo ya está en uso");
            }
        }

        // validaciones de dinero
        if (clienteActualizado.getDeudaActual() < 0) {
            throw new RuntimeException("La deuda no puede ser negativa");
        }

        if (clienteActualizado.getLimiteCredito() < 0) {
            throw new RuntimeException("El límite de crédito no puede ser negativo");
        }

        if (clienteActualizado.getDeudaActual() > clienteActualizado.getLimiteCredito()) {
            throw new RuntimeException("La deuda no puede superar el límite de crédito");
        }

        // actualizar campos
        existe.setNombre(clienteActualizado.getNombre());
        existe.setApodo(clienteActualizado.getApodo());
        existe.setTelefono(clienteActualizado.getTelefono());
        existe.setDeudaActual(clienteActualizado.getDeudaActual());
        existe.setLimiteCredito(clienteActualizado.getLimiteCredito());

        return clienteRepository.save(existe);
    }

    // ELIMINAR
    public void eliminar(Long id) {

        Cliente existe = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el cliente para eliminar"));

        // regla de negocio: no eliminar si tiene deuda
        if (existe.getDeudaActual() > 0) {
            throw new RuntimeException("No se puede eliminar un cliente con deuda pendiente");
        }

        clienteRepository.delete(existe);
    }
}