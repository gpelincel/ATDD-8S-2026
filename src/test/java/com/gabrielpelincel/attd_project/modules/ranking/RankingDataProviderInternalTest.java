package com.gabrielpelincel.attd_project.modules.ranking;

import com.gabrielpelincel.attd_project.modules.cursos.domain.Aluno;
import com.gabrielpelincel.attd_project.modules.cursos.repository.AlunoRepository;
import com.gabrielpelincel.attd_project.modules.ranking.service.RankingDataProvider;
import com.gabrielpelincel.attd_project.modules.ranking.service.RankingDataProviderInternal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Integração Modular: RankingDataProviderInternal (com Mockito @Mock e @InjectMocks)")
public class RankingDataProviderInternalTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private RankingDataProviderInternal rankingDataProviderInternal;

    @Test
    @DisplayName("Deve converter lista de alunos em itens de ranking para desacoplamento de fronteira")
    void deveObterDadosAlunosParaRanking() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setAssinatura("Básica");
        aluno1.setCursosConquistados(5);
        aluno1.setMoedas(1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setAssinatura("Premium");
        aluno2.setCursosConquistados(14);
        aluno2.setMoedas(6);

        when(alunoRepository.findAll()).thenReturn(List.of(aluno1, aluno2));

        List<RankingDataProvider.AlunoRankingItem> itens = rankingDataProviderInternal.obterDadosAlunosParaRanking();

        assertEquals(2, itens.size());
        assertEquals(1L, itens.get(0).id());
        assertEquals("Básica", itens.get(0).assinatura());
        assertEquals(5, itens.get(0).cursosConquistados());
        assertEquals(1, itens.get(0).moedas());

        assertEquals(2L, itens.get(1).id());
        assertEquals("Premium", itens.get(1).assinatura());
        assertEquals(14, itens.get(1).cursosConquistados());
        assertEquals(6, itens.get(1).moedas());

        verify(alunoRepository, times(1)).findAll();
        verifyNoMoreInteractions(alunoRepository);
    }
}
