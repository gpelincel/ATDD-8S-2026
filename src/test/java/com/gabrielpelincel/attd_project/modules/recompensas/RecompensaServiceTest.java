package com.gabrielpelincel.attd_project.modules.recompensas;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import com.gabrielpelincel.attd_project.modules.recompensas.service.ProjetoRealService;
import com.gabrielpelincel.attd_project.modules.recompensas.service.RecompensaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RecompensaServiceTest {

    @Mock
    private ProjetoRealService projetoRealService;

    private RecompensaService recompensaService;

    @BeforeEach
    void setUp() {
        recompensaService = new RecompensaService(projetoRealService);
    }

    @Test
    void deveAplicarVoucherEmProjetoQuandoSaldoPositivo() {
        ProjetoReal projeto = new ProjetoReal("Projeto Alpha");
        assertFalse(projeto.isVoucherAplicado());

        boolean resgatado = recompensaService.resgatarVoucherEmProjeto(1, projeto);

        assertTrue(resgatado);
        assertTrue(projeto.isVoucherAplicado());
    }

    @Test
    void naoDeveAplicarVoucherQuandoSaldoForZeroOuNegativo() {
        ProjetoReal projeto = new ProjetoReal("Projeto Beta");

        boolean resgatado = recompensaService.resgatarVoucherEmProjeto(0, projeto);

        assertFalse(resgatado);
        assertFalse(projeto.isVoucherAplicado());
    }
}
