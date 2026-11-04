package com.gabrielpelincel.attd_project.modules.recompensas.dto;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;

public class ProjetoRealResponseDTO {
    private Long id;
    private String nome;
    private boolean voucherAplicado;

    public ProjetoRealResponseDTO() {
    }

    public ProjetoRealResponseDTO(ProjetoReal projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.voucherAplicado = projeto.isVoucherAplicado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isVoucherAplicado() {
        return voucherAplicado;
    }

    public void setVoucherAplicado(boolean voucherAplicado) {
        this.voucherAplicado = voucherAplicado;
    }
}
