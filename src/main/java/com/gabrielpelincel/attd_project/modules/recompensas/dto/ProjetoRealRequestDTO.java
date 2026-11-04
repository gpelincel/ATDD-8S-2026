package com.gabrielpelincel.attd_project.modules.recompensas.dto;

public class ProjetoRealRequestDTO {
    private String nome;

    public ProjetoRealRequestDTO() {
    }

    public ProjetoRealRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
