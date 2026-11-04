package com.gabrielpelincel.attd_project.modules.ranking.dto;

public class PosicaoRankingDTO {
    private int posicao;
    private Long alunoId;
    private String assinatura;
    private int cursosConquistados;
    private int moedas;

    public PosicaoRankingDTO() {
    }

    public PosicaoRankingDTO(int posicao, Long alunoId, String assinatura, int cursosConquistados, int moedas) {
        this.posicao = posicao;
        this.alunoId = alunoId;
        this.assinatura = assinatura;
        this.cursosConquistados = cursosConquistados;
        this.moedas = moedas;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
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
}
