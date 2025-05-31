package com.banco.service;

import com.banco.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {

    Cuenta createCuenta(Cuenta cuenta);

    List<Cuenta> getAllCuenta();

    Optional<Cuenta> getCuentaById(Integer id);

    Optional<Cuenta> getByNumeroCuenta(String numeroCuenta);

    Cuenta updateCuenta(Integer id, Cuenta cuenta);

    List<Cuenta> getCuentasByClienteId(Integer clienteId);

    void deleteCliente(Integer id);
}