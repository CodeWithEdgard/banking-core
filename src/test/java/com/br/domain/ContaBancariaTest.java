package com.br.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.br.exception.ContaBloqueadaException;
import com.br.exception.SaldoInsuficienteException;
import com.br.exception.ValorInvalidoException;

public class ContaBancariaTest {

    ContaBancaria usuario;

    @BeforeEach
    void setUp() {
        usuario = new ContaBancaria("Edgar");
    }

    @Test
    void depositoComSucesso() {

        BigDecimal valorParaDepositar = BigDecimal.valueOf(132.97);

        usuario.depositar(valorParaDepositar);

        assertEquals(valorParaDepositar, usuario.getSaldo());

    }

    @Test
    void depositoComValorNegativo() {

        BigDecimal valorParaDepositar = BigDecimal.valueOf(-5000);

        ValorInvalidoException e = assertThrows(ValorInvalidoException.class,
                () -> usuario.depositar(valorParaDepositar));

        assertTrue(e.getMessage().equals("valor invalido"));

    }

    @Test
    void depositoComContaBloqueada() {

        BigDecimal valorParaDepositar = BigDecimal.valueOf(1500.30);

        usuario.bloquearConta();

        ContaBloqueadaException e = assertThrows(ContaBloqueadaException.class,
                () -> usuario.depositar(valorParaDepositar));

        assertTrue(e.getMessage().equals("conta esta bloqueada"));
    }

    @Test
    void sacarComSucesso() {

        BigDecimal valorParaDepositar = BigDecimal.valueOf(1000);
        BigDecimal valorParaSaque = BigDecimal.valueOf(500);
        BigDecimal saldoRestante = BigDecimal.valueOf(500);

        usuario.depositar(valorParaDepositar);
        usuario.sacar(valorParaSaque);

        assertEquals(saldoRestante, usuario.getSaldo());

    }

    @Test
    void sacarComSaldoInsuficiente() {

        BigDecimal valorParaSaque = BigDecimal.valueOf(500);

        SaldoInsuficienteException e =
                assertThrows(SaldoInsuficienteException.class, () -> usuario.sacar(valorParaSaque));

        assertTrue(e.getMessage().equals("Saldo Insuficiente"));
    }

    @Test
    void sacarComContaBloqueada() {

        BigDecimal valorParaDepositar = BigDecimal.valueOf(1000);
        BigDecimal valorParaSaque = BigDecimal.valueOf(500);

        usuario.depositar(valorParaDepositar);
        usuario.bloquearConta();

        ContaBloqueadaException e =
                assertThrows(ContaBloqueadaException.class, () -> usuario.sacar(valorParaSaque));

        assertTrue(e.getMessage().equals("Conta esta Bloqueada"));
    }

    @Test
    void criarContaComCaracteresInvalidos() {

        IllegalArgumentException e =
                assertThrows(IllegalArgumentException.class, () -> new ContaBancaria("Edgar4 "));

        assertTrue(e.getMessage().equals("Nome contém caracteres inválidos"));
    }
}
