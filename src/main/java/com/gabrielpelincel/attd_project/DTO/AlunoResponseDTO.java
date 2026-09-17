package com.gabrielpelincel.attd_project.DTO;

import com.gabrielpelincel.attd_project.Domain.Aluno;

public class AlunoResponseDTO {
    private Long id;
    private String assinatura;
    private int cursosConquistados;
    private int moedas;
    private int vouchers;
    private boolean acessoVouchers;
    private boolean acessoProjetosReais;

    public AlunoResponseDTO(Aluno aluno) {
        this.id = aluno.getId();
        this.assinatura = aluno.getAssinatura();
        this.cursosConquistados = aluno.getCursosConquistados();
        this.moedas = aluno.getMoedas();
        this.vouchers = aluno.getVouchers();
        this.acessoVouchers = aluno.isAcessoVouchers();
        this.acessoProjetosReais = aluno.isAcessoProjetosReais();
    }

    public Long getId() {
        return id;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public int getCursosConquistados() {
        return cursosConquistados;
    }

    public int getMoedas() {
        return moedas;
    }

    public int getVouchers() {
        return vouchers;
    }

    public boolean isAcessoVouchers() {
        return acessoVouchers;
    }

    public boolean isAcessoProjetosReais() {
        return acessoProjetosReais;
    }
}
