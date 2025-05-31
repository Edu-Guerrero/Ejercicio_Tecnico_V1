package com.banco.service;

import com.banco.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Cliente createCliente(Cliente cliente);

    List<Cliente> getAllClientes();

    Optional<Cliente> getClienteById(Integer id);

    Cliente updateCliente(Integer id, Cliente cliente);

    void deleteCliente(Integer id);
}