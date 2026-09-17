package com.gabrielpelincel.attd_project.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assinatura;
    private int cursosConquistados;
    private int moedas;
    private int vouchers;
    private boolean acessoVouchers;
    private boolean acessoProjetosReais;

    public Aluno() {
        this.assinatura = "Básica";
        this.cursosConquistados = 0;
        this.moedas = 0;
        this.vouchers = 0;
        this.acessoVouchers = false;
        this.acessoProjetosReais = false;
    }

    public void conquistarCurso() {
        this.cursosConquistados++;

        if (this.cursosConquistados >= 12 && !this.assinatura.equals("Premium")) {
            this.assinatura = "Premium";
            this.vouchers += 1;
            this.acessoProjetosReais = true;
            this.moedas += 3;
            this.acessoVouchers = true;
        }
    }

    public void resgatarVoucher(ProjetoReal projeto) {
        if (this.vouchers > 0) {
            this.vouchers--;
            projeto.aplicarVoucher();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAssinatura() { return assinatura; }
    public void setAssinatura(String assinatura) { this.assinatura = assinatura; }

    public int getCursosConquistados() { return cursosConquistados; }
    public void setCursosConquistados(int cursosConquistados) { this.cursosConquistados = cursosConquistados; }

    public int getMoedas() { return moedas; }
    public void setMoedas(int moedas) { this.moedas = moedas; }

    public int getVouchers() { return vouchers; }
    public void setVouchers(int vouchers) { this.vouchers = vouchers; }

    public boolean isAcessoVouchers() { return acessoVouchers; }
    public void setAcessoVouchers(boolean acessoVouchers) { this.acessoVouchers = acessoVouchers; }

    public boolean isAcessoProjetosReais() { return acessoProjetosReais; }
    public void setAcessoProjetosReais(boolean acessoProjetosReais) { this.acessoProjetosReais = acessoProjetosReais; }
}
