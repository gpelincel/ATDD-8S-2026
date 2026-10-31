package com.gabrielpelincel.attd_project.modules.ranking;

import com.gabrielpelincel.attd_project.modules.ranking.dto.PosicaoRankingDTO;
import com.gabrielpelincel.attd_project.modules.ranking.service.RankingDataProvider;
import com.gabrielpelincel.attd_project.modules.ranking.service.RankingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RankingServiceTest {

    @Mock
    private RankingDataProvider rankingDataProvider;

    private RankingServiceImpl rankingService;

    @BeforeEach
    void setUp() {
        rankingService = new RankingServiceImpl(rankingDataProvider);
    }

    @Test
    void deveOrdenarAlunosPorCursosConquistadosEMoedas() {
        List<RankingDataProvider.AlunoRankingItem> mockItems = List.of(
                new RankingDataProvider.AlunoRankingItem(1L, "Básica", 5, 0),
                new RankingDataProvider.AlunoRankingItem(2L, "Premium", 15, 6),
                new RankingDataProvider.AlunoRankingItem(3L, "Premium", 12, 3),
                new RankingDataProvider.AlunoRankingItem(4L, "Premium", 12, 9)
        );

        when(rankingDataProvider.obterDadosAlunosParaRanking()).thenReturn(mockItems);

        List<PosicaoRankingDTO> ranking = rankingService.obterRanking();

        assertEquals(4, ranking.size());

        // 1º lugar: 15 cursos
        assertEquals(1, ranking.get(0).getPosicao());
        assertEquals(2L, ranking.get(0).getAlunoId());

        // 2º lugar: 12 cursos, 9 moedas (desempate)
        assertEquals(2, ranking.get(1).getPosicao());
        assertEquals(4L, ranking.get(1).getAlunoId());

        // 3º lugar: 12 cursos, 3 moedas
        assertEquals(3, ranking.get(2).getPosicao());
        assertEquals(3L, ranking.get(2).getAlunoId());

        // 4º lugar: 5 cursos
        assertEquals(4, ranking.get(3).getPosicao());
        assertEquals(1L, ranking.get(3).getAlunoId());
    }

    @Test
    void deveObterPosicaoIndividualDoAluno() {
        List<RankingDataProvider.AlunoRankingItem> mockItems = List.of(
                new RankingDataProvider.AlunoRankingItem(10L, "Premium", 20, 10),
                new RankingDataProvider.AlunoRankingItem(20L, "Básica", 2, 0)
        );

        when(rankingDataProvider.obterDadosAlunosParaRanking()).thenReturn(mockItems);

        PosicaoRankingDTO posicao = rankingService.obterPosicaoPorAlunoId(20L);

        assertEquals(2, posicao.getPosicao());
        assertEquals(20L, posicao.getAlunoId());
    }
}
