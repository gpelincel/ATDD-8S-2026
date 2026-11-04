package com.gabrielpelincel.attd_project.modules.recompensas;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import com.gabrielpelincel.attd_project.modules.recompensas.service.ProjetoRealService;
import com.gabrielpelincel.attd_project.modules.recompensas.service.RecompensaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Serviço: RecompensaService")
public class RecompensaServiceTest {

    @Mock
    private ProjetoRealService projetoRealService;

    private RecompensaService recompensaService;

    @BeforeEach
    void setUp() {
        recompensaService = new RecompensaService(projetoRealService);
    }

    @Test
    @DisplayName("Deve aplicar voucher em projeto sem ID sem tentar persistir")
    void deveAplicarVoucherEmProjetoQuandoSaldoPositivo() {
        ProjetoReal projeto = new ProjetoReal("Projeto Alpha");
        assertFalse(projeto.isVoucherAplicado());

        boolean resgatado = recompensaService.resgatarVoucherEmProjeto(1, projeto);

        assertTrue(resgatado);
        assertTrue(projeto.isVoucherAplicado());
        verify(projetoRealService, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve aplicar voucher e persistir quando projeto possui ID")
    void deveAplicarVoucherEPersistirQuandoProjetoPossuiId() {
        ProjetoReal projeto = new ProjetoReal("Projeto Persistido");
        projeto.setId(5L);

        boolean resgatado = recompensaService.resgatarVoucherEmProjeto(2, projeto);

        assertTrue(resgatado);
        assertTrue(projeto.isVoucherAplicado());
        verify(projetoRealService).salvar(projeto);
    }

    @Test
    @DisplayName("Não deve aplicar voucher quando saldo for zero ou negativo")
    void naoDeveAplicarVoucherQuandoSaldoForZeroOuNegativo() {
        ProjetoReal projeto = new ProjetoReal("Projeto Beta");

        boolean resgatado = recompensaService.resgatarVoucherEmProjeto(0, projeto);

        assertFalse(resgatado);
        assertFalse(projeto.isVoucherAplicado());
        verify(projetoRealService, never()).salvar(any());
    }

    @Test
    @DisplayName("Não deve aplicar voucher quando o projeto for nulo")
    void naoDeveAplicarVoucherQuandoProjetoForNulo() {
        boolean resgatado = recompensaService.resgatarVoucherEmProjeto(1, null);

        assertFalse(resgatado);
        verify(projetoRealService, never()).salvar(any());
    }
}
