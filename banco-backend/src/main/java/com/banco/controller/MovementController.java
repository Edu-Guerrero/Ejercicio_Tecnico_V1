package com.banco.controller;


import com.banco.model.Movimiento;
import com.banco.service.MovimientoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movements")
public class MovementController {

    private final MovimientoService movimientoService;

    public MovementController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    //POST
    @PostMapping
    public ResponseEntity<?> createMovement(@RequestBody Movimiento movimiento) {
        try {
            Movimiento createdMovement = movimientoService.createMovimiento(movimiento);
            return ResponseEntity.ok(createdMovement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //GET
    @GetMapping
    public ResponseEntity<List<Movimiento>> getAllMovements() {
        List<Movimiento> movements = movimientoService.getAllMovimientos();
        return ResponseEntity.ok(movements);
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> getMovementById(@PathVariable Integer id) {
        return movimientoService.getMovimientoById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Movement not found with id: " + id));
    }

    //GET
    @GetMapping("/account/{cuentaId}")
    public ResponseEntity<List<Movimiento>> getMovementsByAccountId(
        @PathVariable Integer cuentaId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String endDate
    ) 
    {
        List<Movimiento> movements = movimientoService.getMovimientosByCuentaIdAndFechaBetween(cuentaId, startDate, endDate);
        if (movements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movements);
    }

    //GET
    @GetMapping("/number/{numeroCuenta}")
    public ResponseEntity<List<Movimiento>> getMovementsByAccountNumber(
        @PathVariable String numeroCuenta,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String endDate
    ) 
    {
        List<Movimiento> movements = movimientoService.getMovimientosByNumeroCuentaAndFechaBetween(numeroCuenta, startDate, endDate);
        if (movements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movements);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Integer id) {
        try {
            movimientoService.deleteMovimiento(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
