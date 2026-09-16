package com.gabrielpelincel.attd_project.Domain;

public class ProjetoReal {
    private boolean voucherAplicado;

    public ProjetoReal() {
        this.voucherAplicado = false;
    }

    public void aplicarVoucher() {
        this.voucherAplicado = true;
    }

    public boolean isVoucherAplicado() {
        return voucherAplicado;
    }
}
