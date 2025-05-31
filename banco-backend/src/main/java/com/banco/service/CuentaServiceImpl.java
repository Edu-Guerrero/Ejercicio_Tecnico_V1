package com.banco.service;

import com.banco.model.Cuenta;
import com.banco.repository.CuentaRepository;
import com.banco.service.CuentaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Cuenta createCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public List<Cuenta> getAllCuenta() {
        return cuentaRepository.findAll();
    }

    @Override
    public Optional<Cuenta> getCuentaById(Integer id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public Optional<Cuenta> getByNumeroCuenta(String numeroCuenta) {
        return Optional.ofNullable(cuentaRepository.findByNumeroCuenta(numeroCuenta));
    }

    @Override
    public Cuenta updateCuenta(Integer id, Cuenta cuenta) {
        Cuenta existingCuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuenta not found with id: " + id));

        existingCuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
        existingCuenta.setSaldo(cuenta.getSaldo());
        existingCuenta.setTipoCuenta(cuenta.getTipoCuenta());
        existingCuenta.setEstado(cuenta.getEstado());
        existingCuenta.setCliente(cuenta.getCliente());

        return cuentaRepository.save(existingCuenta);
    }

    @Override
    public List<Cuenta> getCuentasByClienteId(Integer clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

    @Override
    public void deleteCliente(Integer id) {
        if (!cuentaRepository.existsById(id)) {
            throw new EntityNotFoundException("Cuenta not found with id: " + id);
        }
        cuentaRepository.deleteById(id);
    }
}