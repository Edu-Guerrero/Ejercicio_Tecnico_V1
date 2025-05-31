package com.banco.controller;

import com.banco.model.Cliente;
import com.banco.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final ClienteService clienteService;

    public CustomerController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //POST
    @PostMapping
    public ResponseEntity<Cliente> createCustomer(@RequestBody Cliente cliente) {
        Cliente createdCliente = clienteService.createCliente(cliente);
        return ResponseEntity.ok(createdCliente);
    }

    //GET
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllCustomers() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    //GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getCustomerById(@PathVariable Integer id) {
        Cliente cliente = clienteService.getClienteById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente not found with id: " + id));
        return ResponseEntity.ok(cliente);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCustomer(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente updatedCliente = clienteService.updateCliente(id, cliente);
            return ResponseEntity.ok(updatedCliente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
