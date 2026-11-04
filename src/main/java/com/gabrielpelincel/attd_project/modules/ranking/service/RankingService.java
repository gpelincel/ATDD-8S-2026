package com.gabrielpelincel.attd_project.modules.ranking.service;

import com.gabrielpelincel.attd_project.modules.ranking.dto.PosicaoRankingDTO;

import java.util.List;

public interface RankingService {

    List<PosicaoRankingDTO> obterRanking();

    PosicaoRankingDTO obterPosicaoPorAlunoId(Long alunoId);
}
