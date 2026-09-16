package com.gabrielpelincel.attd_project.DomainTest;

import com.gabrielpelincel.attd_project.Domain.Aluno;
import com.gabrielpelincel.attd_project.Domain.ProjetoReal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {
    @Test
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
    public void devePermitirResgateDeVoucherEmProjetoRealAposTornarPremium() {
        Aluno aluno = new Aluno();
        aluno.setAssinatura("Básica");
        aluno.setCursosConquistados(11);
        aluno.setVouchers(0);

        ProjetoReal projetoSemSaldo = new ProjetoReal();
        ProjetoReal projetoComSaldo = new ProjetoReal();

        aluno.resgatarVoucher(projetoSemSaldo);
        assertFalse(projetoSemSaldo.isVoucherAplicado(), "Não deve aplicar voucher se não tiver saldo");

        aluno.conquistarCurso();

        aluno.resgatarVoucher(projetoComSaldo);

        assertEquals("Premium", aluno.getAssinatura(), "A assinatura deveria ser Premium");
        assertEquals(0, aluno.getVouchers(), "O voucher deveria ter sido descontado");
        assertTrue(projetoComSaldo.isVoucherAplicado(), "O voucher deveria ter sido aplicado no projeto");
    }
}
