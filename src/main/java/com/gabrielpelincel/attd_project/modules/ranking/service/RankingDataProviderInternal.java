package com.gabrielpelincel.attd_project.modules.ranking.service;

import com.gabrielpelincel.attd_project.modules.cursos.repository.AlunoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RankingDataProviderInternal implements RankingDataProvider {

    private final AlunoRepository alunoRepository;

    public RankingDataProviderInternal(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public List<AlunoRankingItem> obterDadosAlunosParaRanking() {
        return alunoRepository.findAll().stream()
                .map(aluno -> new AlunoRankingItem(
                        aluno.getId(),
                        aluno.getAssinatura(),
                        aluno.getCursosConquistados(),
                        aluno.getMoedas()
                ))
                .collect(Collectors.toList());
    }
}
