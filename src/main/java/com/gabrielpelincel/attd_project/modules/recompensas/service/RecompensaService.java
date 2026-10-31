package com.gabrielpelincel.attd_project.modules.recompensas.service;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import org.springframework.stereotype.Service;

@Service
public class RecompensaService {

    private final ProjetoRealService projetoRealService;

    public RecompensaService(ProjetoRealService projetoRealService) {
        this.projetoRealService = projetoRealService;
    }

    /**
     * Aplica um voucher a um projeto real, persistindo o estado se o projeto possuir ID.
     */
    public boolean resgatarVoucherEmProjeto(int saldoVouchers, ProjetoReal projeto) {
        if (saldoVouchers > 0 && projeto != null) {
            projeto.aplicarVoucher();
            if (projeto.getId() != null) {
                projetoRealService.salvar(projeto);
            }
            return true;
        }
        return false;
    }
}
