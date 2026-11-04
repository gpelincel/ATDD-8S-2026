package com.gabrielpelincel.attd_project.modules.ranking.service;

import com.gabrielpelincel.attd_project.modules.ranking.dto.PosicaoRankingDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    private final RankingDataProvider rankingDataProvider;

    public RankingServiceImpl(RankingDataProvider rankingDataProvider) {
        this.rankingDataProvider = rankingDataProvider;
    }

    @Override
    public List<PosicaoRankingDTO> obterRanking() {
        List<RankingDataProvider.AlunoRankingItem> dados = rankingDataProvider.obterDadosAlunosParaRanking();

        List<RankingDataProvider.AlunoRankingItem> ordenados = dados.stream()
                .sorted(Comparator.comparingInt(RankingDataProvider.AlunoRankingItem::cursosConquistados)
                        .thenComparingInt(RankingDataProvider.AlunoRankingItem::moedas)
                        .reversed())
                .toList();

        List<PosicaoRankingDTO> ranking = new ArrayList<>();
        int posicao = 1;
        for (RankingDataProvider.AlunoRankingItem item : ordenados) {
            ranking.add(new PosicaoRankingDTO(
                    posicao++,
                    item.id(),
                    item.assinatura(),
                    item.cursosConquistados(),
                    item.moedas()
            ));
        }

        return ranking;
    }

    @Override
    public PosicaoRankingDTO obterPosicaoPorAlunoId(Long alunoId) {
        List<PosicaoRankingDTO> ranking = obterRanking();
        return ranking.stream()
                .filter(pos -> pos.getAlunoId().equals(alunoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado no ranking: " + alunoId));
    }
}
