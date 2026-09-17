package com.gabrielpelincel.attd_project.DTO;

import com.gabrielpelincel.attd_project.Domain.ProjetoReal;

public class ProjetoRealResponseDTO {
    private Long id;
    private String nome;
    private boolean voucherAplicado;

    public ProjetoRealResponseDTO(ProjetoReal projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.voucherAplicado = projeto.isVoucherAplicado();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public boolean isVoucherAplicado() { return voucherAplicado; }
}
