package com.gabrielpelincel.attd_project.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProjetoReal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private boolean voucherAplicado;

    public ProjetoReal() {
        this.voucherAplicado = false;
    }

    public ProjetoReal(String nome) {
        this.nome = nome;
        this.voucherAplicado = false;
    }

    public void aplicarVoucher() {
        this.voucherAplicado = true;
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
