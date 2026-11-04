package com.gabrielpelincel.attd_project.modules.cursos;

import com.gabrielpelincel.attd_project.modules.cursos.controller.AlunoController;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoResponseDTO;
import com.gabrielpelincel.attd_project.modules.cursos.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Controller: AlunoController")
public class AlunoControllerTest {

    @Mock
    private AlunoService alunoService;

    private AlunoController alunoController;

    @BeforeEach
    void setUp() {
        alunoController = new AlunoController(alunoService);
    }

    @Test
    @DisplayName("POST /api/alunos - Deve criar aluno com DTO informado")
    void deveCriarAlunoComDtoInformado() {
        AlunoRequestDTO request = new AlunoRequestDTO("Premium");
        AlunoResponseDTO responseDTO = new AlunoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setAssinatura("Premium");

        when(alunoService.criarAluno(any(AlunoRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<AlunoResponseDTO> response = alunoController.criarAluno(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Premium", response.getBody().getAssinatura());
    }

    @Test
    @DisplayName("POST /api/alunos - Deve criar aluno com requestDTO nulo")
    void deveCriarAlunoComRequestNulo() {
        AlunoResponseDTO responseDTO = new AlunoResponseDTO();
        responseDTO.setId(2L);
        responseDTO.setAssinatura("Básica");

        when(alunoService.criarAluno(any(AlunoRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<AlunoResponseDTO> response = alunoController.criarAluno(null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getId());
    }

    @Test
    @DisplayName("POST /api/alunos/{id}/conquistar-curso - Deve retornar 200 OK no sucesso")
    void deveRetornarOkAoConquistarCurso() {
        AlunoResponseDTO responseDTO = new AlunoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setCursosConquistados(12);

        when(alunoService.conquistarCurso(1L)).thenReturn(responseDTO);

        ResponseEntity<?> response = alunoController.conquistarCurso(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("POST /api/alunos/{id}/conquistar-curso - Deve retornar 404 Not Found quando aluno não existe")
    void deveRetornarNotFoundAoConquistarCursoInexistente() {
        when(alunoService.conquistarCurso(99L)).thenThrow(new RuntimeException("Aluno não encontrado"));

        ResponseEntity<?> response = alunoController.conquistarCurso(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Aluno não encontrado", response.getBody());
    }

    @Test
    @DisplayName("POST /api/alunos/{id}/resgatar-voucher - Deve retornar 200 OK no sucesso")
    void deveRetornarOkAoResgatarVoucher() {
        AlunoResponseDTO responseDTO = new AlunoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setVouchers(0);

        when(alunoService.resgatarVoucher(1L)).thenReturn(responseDTO);

        ResponseEntity<?> response = alunoController.resgatarVoucher(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("POST /api/alunos/{id}/resgatar-voucher - Deve retornar 400 Bad Request em caso de erro")
    void deveRetornarBadRequestAoFalharResgateDeVoucher() {
        when(alunoService.resgatarVoucher(1L)).thenThrow(new RuntimeException("Sem saldo"));

        ResponseEntity<?> response = alunoController.resgatarVoucher(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sem saldo", response.getBody());
    }

    @Test
    @DisplayName("GET /api/alunos/{id} - Deve retornar 200 OK com aluno encontrado")
    void deveRetornarOkAoBuscarAlunoPorId() {
        AlunoResponseDTO responseDTO = new AlunoResponseDTO();
        responseDTO.setId(1L);

        when(alunoService.buscarAluno(1L)).thenReturn(responseDTO);

        ResponseEntity<?> response = alunoController.buscarAluno(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("GET /api/alunos/{id} - Deve retornar 404 Not Found quando aluno não existe")
    void deveRetornarNotFoundAoBuscarAlunoInexistente() {
        when(alunoService.buscarAluno(99L)).thenThrow(new RuntimeException("Aluno não encontrado"));

        ResponseEntity<?> response = alunoController.buscarAluno(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Aluno não encontrado", response.getBody());
    }
}
