package com.gabrielpelincel.attd_project.modules.ranking;

import com.gabrielpelincel.attd_project.modules.ranking.controller.RankingController;
import com.gabrielpelincel.attd_project.modules.ranking.dto.PosicaoRankingDTO;
import com.gabrielpelincel.attd_project.modules.ranking.service.RankingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Controller: RankingController (com Mockito @Mock e @InjectMocks)")
public class RankingControllerTest {

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private RankingController rankingController;

    @Test
    @DisplayName("GET /api/ranking - Deve retornar lista com ranking geral via mock")
    void deveRetornarRankingGeral() {
        List<PosicaoRankingDTO> mockRanking = List.of(
                new PosicaoRankingDTO(1, 1L, "Premium", 15, 6),
                new PosicaoRankingDTO(2, 2L, "Básica", 8, 0)
        );

        when(rankingService.obterRanking()).thenReturn(mockRanking);

        ResponseEntity<List<PosicaoRankingDTO>> response = rankingController.obterRanking();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1, response.getBody().get(0).getPosicao());
        verify(rankingService, times(1)).obterRanking();
    }

    @Test
    @DisplayName("GET /api/ranking/alunos/{id} - Deve retornar posição do aluno específico via mock")
    void deveRetornarPosicaoDoAluno() {
        PosicaoRankingDTO posicao = new PosicaoRankingDTO(3, 10L, "Premium", 12, 3);
        when(rankingService.obterPosicaoPorAlunoId(10L)).thenReturn(posicao);

        ResponseEntity<PosicaoRankingDTO> response = rankingController.obterPosicaoAluno(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody().getAlunoId());
        assertEquals(3, response.getBody().getPosicao());
        verify(rankingService, times(1)).obterPosicaoPorAlunoId(10L);
    }
}
