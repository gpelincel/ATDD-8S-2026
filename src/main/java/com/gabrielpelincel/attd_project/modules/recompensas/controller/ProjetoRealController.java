package com.gabrielpelincel.attd_project.modules.recompensas.controller;

import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealRequestDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealResponseDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.service.ProjetoRealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
@CrossOrigin(origins = "*")
public class ProjetoRealController {

    private final ProjetoRealService projetoService;

    public ProjetoRealController(ProjetoRealService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping
    public ResponseEntity<ProjetoRealResponseDTO> criarProjeto(@RequestBody ProjetoRealRequestDTO request) {
        ProjetoRealResponseDTO response = projetoService.criarProjeto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjetoRealResponseDTO>> listarProjetos() {
        List<ProjetoRealResponseDTO> projetos = projetoService.listarProjetos();
        return ResponseEntity.ok(projetos);
    }
}
