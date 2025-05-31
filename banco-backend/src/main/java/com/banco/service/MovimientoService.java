package com.banco.service;

import com.banco.model.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoService {
    Movimiento createMovimiento(Movimiento movimiento);

    List<Movimiento> getAllMovimientos();

    Optional<Movimiento> getMovimientoById(Integer id);

    List<Movimiento> getMovimientosByCuentaIdAndFechaBetween(Integer cuentaId, String startDate, String endDate);

    List<Movimiento> getMovimientosByNumeroCuentaAndFechaBetween(String numeroCuenta, String startDate, String endDate);

    void deleteMovimiento(Integer id);
}