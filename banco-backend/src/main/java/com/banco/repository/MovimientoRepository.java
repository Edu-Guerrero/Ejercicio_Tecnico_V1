package com.banco.repository;

import com.banco.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    List<Movimiento> findByCuenta_IdAndFechaBetween(Integer cuentaId, LocalDateTime start, LocalDateTime end);

    List<Movimiento> findByCuenta_NumeroCuentaAndFechaBetween(String numeroCuenta, LocalDateTime start, LocalDateTime end);
}
