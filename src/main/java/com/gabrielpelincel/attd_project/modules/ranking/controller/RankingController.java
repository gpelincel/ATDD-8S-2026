package com.gabrielpelincel.attd_project.modules.ranking.controller;

import com.gabrielpelincel.attd_project.modules.ranking.dto.PosicaoRankingDTO;
import com.gabrielpelincel.attd_project.modules.ranking.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@CrossOrigin(origins = "*")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public ResponseEntity<List<PosicaoRankingDTO>> obterRanking() {
        return ResponseEntity.ok(rankingService.obterRanking());
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<PosicaoRankingDTO> obterPosicaoAluno(@PathVariable Long id) {
        return ResponseEntity.ok(rankingService.obterPosicaoPorAlunoId(id));
    }
}
