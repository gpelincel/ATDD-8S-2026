package com.gabrielpelincel.attd_project.modules.cursos.dto;

import com.gabrielpelincel.attd_project.modules.cursos.domain.Aluno;

public class AlunoResponseDTO {
    private Long id;
    private String assinatura;
    private int cursosConquistados;
    private int moedas;
    private int vouchers;
    private boolean acessoVouchers;
    private boolean acessoProjetosReais;

    public AlunoResponseDTO() {
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public int getCursosConquistados() {
        return cursosConquistados;
    }

    public void setCursosConquistados(int cursosConquistados) {
        this.cursosConquistados = cursosConquistados;
    }

    public int getMoedas() {
        return moedas;
    }

    public void setMoedas(int moedas) {
        this.moedas = moedas;
    }

    public int getVouchers() {
        return vouchers;
    }

    public void setVouchers(int vouchers) {
        this.vouchers = vouchers;
    }

    public boolean isAcessoVouchers() {
        return acessoVouchers;
    }

    public void setAcessoVouchers(boolean acessoVouchers) {
        this.acessoVouchers = acessoVouchers;
    }

    public boolean isAcessoProjetosReais() {
        return acessoProjetosReais;
    }

    public void setAcessoProjetosReais(boolean acessoProjetosReais) {
        this.acessoProjetosReais = acessoProjetosReais;
    }
}
