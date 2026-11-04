package com.gabrielpelincel.attd_project.modules.conquistas.service;

import org.springframework.stereotype.Service;

/**
 * Módulo Conquistas: Responsável pelas regras de progressão de nível,
 * marcos de gamificação e atribuição de benefícios do aluno.
 */
@Service
public class ConquistaService {

    public static final int META_CURSOS_PREMIUM = 12;
    public static final String ASSINATURA_BASICA = "Básica";
    public static final String ASSINATURA_PREMIUM = "Premium";
    public static final int MOEDAS_BONUS_PREMIUM = 3;
    public static final int VOUCHERS_BONUS_PREMIUM = 1;

    /**
     * Avalia se o aluno atingiu os requisitos para subir de nível para Premium.
     */
    public boolean isElegivelParaPremium(int cursosConquistados, String assinaturaAtual) {
        return cursosConquistados >= META_CURSOS_PREMIUM && !ASSINATURA_PREMIUM.equalsIgnoreCase(assinaturaAtual);
    }
}
