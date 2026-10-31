package com.gabrielpelincel.attd_project.modules.ranking.service;

import java.util.List;

/**
 * Contrato de isolamento de fronteira (Anti-Corruption / Integration Contract).
 * Garante que o módulo de Ranking não dependa diretamente das entidades internas
 * do módulo de Cursos, facilitando a extração futura para microsserviço conforme ADR-001.
 */
public interface RankingDataProvider {

    record AlunoRankingItem(Long id, String assinatura, int cursosConquistados, int moedas) {}

    List<AlunoRankingItem> obterDadosAlunosParaRanking();
}
