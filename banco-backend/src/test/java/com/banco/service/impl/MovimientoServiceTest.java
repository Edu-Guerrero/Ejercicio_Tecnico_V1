package com.banco.service.impl;

import com.banco.service.MovimientoServiceImpl;
import com.banco.model.Cuenta;
import com.banco.model.Movimiento;
import com.banco.repository.CuentaRepository;
import com.banco.repository.MovimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovimientoServiceTest {

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = new Cuenta();
        cuenta.setId(1);
        cuenta.setSaldo(1000.0);
    }

    @Test
    public void testCreditoMovimientoValido() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(200.0);
        movimiento.setTipoMovimiento("CREDITO");
        movimiento.setCuenta(cuenta);

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        Movimiento resultado = movimientoService.createMovimiento(movimiento);

        assertEquals(1200.0, resultado.getSaldo());
        assertEquals(1200.0, cuenta.getSaldo());
        verify(movimientoRepository).save(any(Movimiento.class));
    }

    @Test
    public void testDebitoMovimientoValido() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(300.0);
        movimiento.setTipoMovimiento("DEBITO");
        movimiento.setCuenta(cuenta);

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        Movimiento resultado = movimientoService.createMovimiento(movimiento);

        assertEquals(700.0, resultado.getSaldo());
        assertEquals(700.0, cuenta.getSaldo());
    }

    @Test
    public void testMovimientoConValorCeroDebeFallar() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(0.0);
        movimiento.setTipoMovimiento("CREDITO");
        movimiento.setCuenta(cuenta);

        assertThrows(IllegalArgumentException.class, () -> movimientoService.createMovimiento(movimiento));
    }

    @Test
    public void testMovimientoConTipoInvalidoDebeFallar() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(100.0);
        movimiento.setTipoMovimiento("TRANSFERENCIA");
        movimiento.setCuenta(cuenta);

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));

        assertThrows(IllegalArgumentException.class, () -> movimientoService.createMovimiento(movimiento));
    }

    @Test
    public void testDebitoConSaldoInsuficienteDebeFallar() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(1500.0); // > saldo actual
        movimiento.setTipoMovimiento("DEBITO");
        movimiento.setCuenta(cuenta);

        when(cuentaRepository.findById(1)).thenReturn(Optional.of(cuenta));

        assertThrows(IllegalArgumentException.class, () -> movimientoService.createMovimiento(movimiento));
    }

    @Test
    public void testCuentaNoExisteDebeFallar() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(100.0);
        movimiento.setTipoMovimiento("CREDITO");
        movimiento.setCuenta(new Cuenta());
        movimiento.getCuenta().setId(99);

        when(cuentaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> movimientoService.createMovimiento(movimiento));
    }
}