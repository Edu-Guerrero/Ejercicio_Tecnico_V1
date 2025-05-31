package com.banco.controller;

import com.banco.model.Cuenta;
import com.banco.service.CuentaService;
import com.banco.repository.CuentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final CuentaService cuentaService;

    public AccountController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    //POST
    @PostMapping
    public ResponseEntity<Cuenta> createAccount(@RequestBody Cuenta cuenta) {
        Cuenta createdAccount = cuentaService.createCuenta(cuenta);
        return ResponseEntity.ok(createdAccount);
    }

    //GET
    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllAccounts() {
        List<Cuenta> accounts = cuentaService.getAllCuenta();
        return ResponseEntity.ok(accounts);
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getAccountById(@PathVariable Integer id) {
        return cuentaService.getCuentaById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
    }

    //GET
    @GetMapping("/number/{numeroCuenta}")
    public ResponseEntity<Cuenta> getAccountByNumber(@PathVariable String numeroCuenta) {
        return cuentaService.getByNumeroCuenta(numeroCuenta)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with number: " + numeroCuenta));
    }

    //GET
    @GetMapping("/customer/{clienteId}")
    public ResponseEntity<List<Cuenta>> getAccountsByCustomerId(@PathVariable Integer clienteId) {
        List<Cuenta> accounts = cuentaService.getCuentasByClienteId(clienteId);
        return ResponseEntity.ok(accounts);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateAccount(@PathVariable Integer id, @RequestBody Cuenta cuenta) {
        try {
            Cuenta updatedAccount = cuentaService.updateCuenta(id, cuenta);
            return ResponseEntity.ok(updatedAccount);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        try {
            cuentaService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
