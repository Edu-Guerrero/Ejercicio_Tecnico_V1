package com.banco.controller;

import com.banco.model.Cuenta;
import com.banco.model.Movimiento;
import com.banco.service.MovimientoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovementController.class)
public class MovimientoControllerTest {

     @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoService movimientoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetMovimientosByCuentaId() throws Exception {
        Movimiento m1 = new Movimiento();
        m1.setId(1);
        m1.setTipoMovimiento("CREDITO");
        m1.setValor(200.0);
        m1.setCuenta(new Cuenta());
        m1.getCuenta().setId(1);

        Movimiento m2 = new Movimiento();
        m2.setId(2);
        m2.setTipoMovimiento("DEBITO");
        m2.setValor(50.0);
        m2.setCuenta(new Cuenta());
        m2.getCuenta().setId(1);

        when(movimientoService.getMovimientosByCuentaIdAndFechaBetween(eq(1), any(), any()))
                .thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/api/v1/movements/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].tipoMovimiento").value("CREDITO"))
                .andExpect(jsonPath("$[1].tipoMovimiento").value("DEBITO"));
    }

    @Test
    void testGetMovimientoById() throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(5);
        movimiento.setTipoMovimiento("DEBITO");
        movimiento.setValor(300.0);
        movimiento.setCuenta(new Cuenta());
        movimiento.getCuenta().setId(1);

        when(movimientoService.getMovimientoById(5)).thenReturn(Optional.of(movimiento));

        mockMvc.perform(get("/api/v1/movements/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.tipoMovimiento").value("DEBITO"))
                .andExpect(jsonPath("$.valor").value(300.0));
    }

    @Test
    void testGetMovimientoById_NotFound() throws Exception {
        when(movimientoService.getMovimientoById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/movements/99"))
                .andExpect(status().isNotFound());
    }
    
}
