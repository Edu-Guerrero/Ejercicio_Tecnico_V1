package com.banco.repository;

import com.banco.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
    Cuenta findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByClienteId(Integer clienteId);
}