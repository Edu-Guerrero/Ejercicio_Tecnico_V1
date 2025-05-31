package com.banco.service;

import com.banco.model.Cliente;
import com.banco.repository.ClienteRepository;
import com.banco.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        logger.info("Creating new cliente wiht id: {}", cliente.getId());
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> getAllClientes() {
        logger.info("Fetching all clientes");
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> getClienteById(Integer id) {
        logger.info("Fetching cliente with id: {}", id);
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente updateCliente(Integer id, Cliente cliente) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente not found with id: " + id));    

        logger.info("Updating cliente with id: {}", id);

        existingCliente.setNombre(cliente.getNombre());
        existingCliente.setGenero(cliente.getGenero());
        existingCliente.setIdentificacion(cliente.getIdentificacion());
        existingCliente.setDireccion(cliente.getDireccion());
        existingCliente.setTelefono(cliente.getTelefono());
        existingCliente.setContrasena(cliente.getContrasena());
        existingCliente.setEstado(cliente.getEstado());

        return clienteRepository.save(existingCliente);
    }

    @Override
    public void deleteCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente not found with id: " + id);
        }
        logger.info("Deleting cliente with id: {}", id);
        clienteRepository.deleteById(id);
    }
    
}
