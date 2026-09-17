package com.gabrielpelincel.attd_project.Controller;

import com.gabrielpelincel.attd_project.DTO.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.DTO.AlunoResponseDTO;
import com.gabrielpelincel.attd_project.Service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criarAluno(@RequestBody AlunoRequestDTO requestDTO) {
        AlunoResponseDTO response = alunoService.criarAluno(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/conquistar-curso")
    public ResponseEntity<?> conquistarCurso(@PathVariable Long id) {
        try {
            AlunoResponseDTO response = alunoService.conquistarCurso(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/resgatar-voucher")
    public ResponseEntity<?> resgatarVoucher(@PathVariable Long id) {
        try {
            AlunoResponseDTO response = alunoService.resgatarVoucher(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
