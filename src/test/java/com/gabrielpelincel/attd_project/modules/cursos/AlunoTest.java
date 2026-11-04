package com.gabrielpelincel.attd_project.modules.cursos;

import com.gabrielpelincel.attd_project.modules.cursos.domain.Aluno;
import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Domínio: Aluno (com Mockito para isolamento modular)")
public class AlunoTest {

    @Mock
    private ProjetoReal projetoMock;

    @Test
    @DisplayName("Cenário 1 TDD: Deve mudar para Premium e ganhar 3 moedas ao atingir 12 cursos")
    public void deveMudarParaPremiumEGanharTresMoedasAoAtingirDozeCursos() {
        Aluno aluno = new Aluno();
        aluno.setAssinatura("Básica");
        aluno.setCursosConquistados(10);
        aluno.setMoedas(0);

        aluno.conquistarCurso();
        assertEquals(11, aluno.getCursosConquistados(), "Deve contabilizar o 11º curso");
        assertEquals("Básica", aluno.getAssinatura(), "Deve continuar com assinatura Básica");
        assertEquals(0, aluno.getMoedas(), "Não deve ganhar moedas ainda");

        aluno.conquistarCurso();
        assertEquals(12, aluno.getCursosConquistados(), "Deve contabilizar o 12º curso");
        assertEquals("Premium", aluno.getAssinatura(), "A assinatura deve mudar para Premium");
        assertEquals(3, aluno.getMoedas(), "O aluno deve ganhar 3 moedas");

        aluno.conquistarCurso();
        assertEquals(13, aluno.getCursosConquistados(), "Deve contabilizar o 13º curso");
        assertEquals("Premium", aluno.getAssinatura(), "Deve se manter Premium");
        assertEquals(3, aluno.getMoedas(), "Não deve ganhar moedas duplicadas ao passar de 12 cursos");
    }

    @Test
    @DisplayName("Deve receber um voucher e acesso a projetos reais ao tornar Premium")
    public void deveReceberUmVoucherEAcessoAProjetosReaisAoTornarPremium() {
        Aluno aluno = new Aluno();
        aluno.setAssinatura("Básica");
        aluno.setCursosConquistados(10);
        aluno.setVouchers(0);
        aluno.setMoedas(0);
        aluno.setAcessoVouchers(false);
        aluno.setAcessoProjetosReais(false);

        aluno.conquistarCurso();
        aluno.conquistarCurso();
        aluno.conquistarCurso();

        assertEquals(13, aluno.getCursosConquistados());
        assertEquals("Premium", aluno.getAssinatura());
        assertTrue(aluno.isAcessoProjetosReais());
    }

    @Test
    @DisplayName("Deve permitir resgate de voucher em mock de ProjetoReal após tornar Premium")
    public void devePermitirResgateDeVoucherEmProjetoRealAposTornarPremium() {
        Aluno aluno = new Aluno();
        aluno.setAssinatura("Básica");
        aluno.setCursosConquistados(11);
        aluno.setVouchers(0);

        // Tentativa de resgate sem saldo de voucher
        aluno.resgatarVoucher(projetoMock);
        verify(projetoMock, never()).aplicarVoucher();

        // Conquista o 12º curso e vira Premium com 1 voucher
        aluno.conquistarCurso();
        assertEquals("Premium", aluno.getAssinatura(), "A assinatura deveria ser Premium");
        assertEquals(1, aluno.getVouchers(), "O aluno deve possuir 1 voucher");

        // Resgata o voucher no projeto mockado
        aluno.resgatarVoucher(projetoMock);

        assertEquals(0, aluno.getVouchers(), "O voucher deveria ter sido descontado");
        verify(projetoMock, times(1)).aplicarVoucher();
    }

    @Test
    @DisplayName("Deve alterar assinatura e liberar acesso a vouchers ao atingir 12 cursos")
    public void deveAlterarAssinaturaELiberarAcessoAVouchersAoAtingirDozeCursos() {
        Aluno aluno = new Aluno();
        aluno.setAssinatura("Básica");
        aluno.setCursosConquistados(10);
        aluno.setAcessoVouchers(false);

        aluno.conquistarCurso();
        assertFalse(aluno.isAcessoVouchers(), "Ainda não deve ter acesso a vouchers com 11 cursos");

        aluno.conquistarCurso();
        assertEquals(12, aluno.getCursosConquistados());
        assertEquals("Premium", aluno.getAssinatura());
        assertTrue(aluno.isAcessoVouchers(), "Deve possuir acesso ao recebimento dos voucher");

        aluno.conquistarCurso();
        assertEquals(13, aluno.getCursosConquistados());
        assertTrue(aluno.isAcessoVouchers());
    }

    @Test
    @DisplayName("Deve descontar voucher mesmo se o projeto for nulo no resgate")
    public void deveDescontarVoucherQuandoProjetoForNulo() {
        Aluno aluno = new Aluno();
        aluno.setVouchers(2);

        aluno.resgatarVoucher(null);

        assertEquals(1, aluno.getVouchers());
    }

    @Test
    @DisplayName("Deve testar getters e setters do Aluno para cobertura completa")
    public void deveTestarGettersESetters() {
        Aluno aluno = new Aluno();
        aluno.setId(100L);
        aluno.setAssinatura("Básica");
        aluno.setCursosConquistados(5);
        aluno.setMoedas(10);
        aluno.setVouchers(4);
        aluno.setAcessoVouchers(true);
        aluno.setAcessoProjetosReais(true);

        assertEquals(100L, aluno.getId());
        assertEquals("Básica", aluno.getAssinatura());
        assertEquals(5, aluno.getCursosConquistados());
        assertEquals(10, aluno.getMoedas());
        assertEquals(4, aluno.getVouchers());
        assertTrue(aluno.isAcessoVouchers());
        assertTrue(aluno.isAcessoProjetosReais());
    }

    @Test
    @DisplayName("Não deve promover novamente se o aluno já for Premium")
    public void naoDevePromoverSeJaForPremium() {
        Aluno aluno = new Aluno();
        aluno.setAssinatura("Premium");
        aluno.setCursosConquistados(12);
        aluno.setMoedas(5);
        aluno.setVouchers(2);

        aluno.conquistarCurso();

        assertEquals(13, aluno.getCursosConquistados());
        assertEquals("Premium", aluno.getAssinatura());
        assertEquals(5, aluno.getMoedas());
        assertEquals(2, aluno.getVouchers());
    }
}
