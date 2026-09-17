package com.gabrielpelincel.attd_project.Controller;

import com.gabrielpelincel.attd_project.DTO.ProjetoRealRequestDTO;
import com.gabrielpelincel.attd_project.DTO.ProjetoRealResponseDTO;
import com.gabrielpelincel.attd_project.Service.ProjetoRealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projetos")
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
