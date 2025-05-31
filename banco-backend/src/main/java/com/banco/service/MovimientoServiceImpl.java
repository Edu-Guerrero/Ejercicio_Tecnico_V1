package com.banco.service;

import com.banco.model.Movimiento;
import com.banco.model.Cuenta;
import com.banco.repository.MovimientoRepository;
import com.banco.repository.CuentaRepository;
import com.banco.service.MovimientoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService{
    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository; // Assuming CuentaRepository is not needed here, otherwise inject it
    }

    @Override
    public Movimiento createMovimiento(Movimiento movimiento) {
        if (movimiento.getValor() <= 0) {
            throw new IllegalArgumentException("El valor del movimiento no puede ser negativo");
        }
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cuenta not found with id: " + movimiento.getCuenta().getId()));

        double saldoInc = cuenta.getSaldo();
        double saldoFin;

        switch (movimiento.getTipoMovimiento()) {
            case "DEBITO":
                if (saldoInc < movimiento.getValor()) {
                    throw new IllegalArgumentException("Saldo no disponible");
                }
                saldoFin = saldoInc - movimiento.getValor();
                break;
            
            case "CREDITO":
                saldoFin = saldoInc + movimiento.getValor();
                break;
            
            default:
                throw new IllegalArgumentException("Tipo de movimiento no válido: " + movimiento.getTipoMovimiento());
        }

        cuenta.setSaldo(saldoFin);
        cuentaRepository.save(cuenta);

        movimiento.setCuenta(cuenta);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldo(saldoFin);

        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Optional<Movimiento> getMovimientoById(Integer id) {
        return movimientoRepository.findById(id);
    }

    @Override
    public List<Movimiento> getMovimientosByCuentaIdAndFechaBetween(Integer cuentaId, String startDate, String endDate) {
        LocalDateTime start;
        LocalDateTime end;
        if (startDate == null || startDate.isEmpty())
        {
            start = LocalDateTime.now().minusDays(30);
        }
        else
        {
            start = LocalDateTime.parse(startDate);
        }
        if (endDate == null || endDate.isEmpty())
        {
            end = LocalDateTime.now();
        }
        else
        {
            end = LocalDateTime.parse(endDate);
        }
        return movimientoRepository.findByCuenta_IdAndFechaBetween(cuentaId, start, end);
    }

    @Override
    public List<Movimiento> getMovimientosByNumeroCuentaAndFechaBetween(String numeroCuenta, String startDate, String endDate) {
        LocalDateTime start;
        LocalDateTime end;
        if (startDate == null || startDate.isEmpty())
        {
            start = LocalDateTime.now().minusDays(30);
        }
        else
        {
            start = LocalDateTime.parse(startDate);
        }
        if (endDate == null || endDate.isEmpty())
        {
            end = LocalDateTime.now();
        }
        else
        {
            end = LocalDateTime.parse(endDate);
        }
        return movimientoRepository.findByCuenta_NumeroCuentaAndFechaBetween(numeroCuenta, start, end);
    }

    @Override
    public void deleteMovimiento(Integer id) {
        if (!movimientoRepository.existsById(id)) {
            throw new EntityNotFoundException("Movimiento not found with id: " + id);
        }
        movimientoRepository.deleteById(id);
    }
}
