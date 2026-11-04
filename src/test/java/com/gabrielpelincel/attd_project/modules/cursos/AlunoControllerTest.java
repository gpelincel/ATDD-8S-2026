package com.gabrielpelincel.attd_project.modules.cursos;

import com.gabrielpelincel.attd_project.modules.cursos.controller.AlunoController;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoResponseDTO;
import com.gabrielpelincel.attd_project.modules.cursos.service.AlunoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Controller: AlunoController (com Mockito @Mock e @InjectMocks)")
public class AlunoControllerTest {

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    @Captor
    private ArgumentCaptor<AlunoRequestDTO> requestCaptor;

    @Test
    @DisplayName("POST /api/alunos - Deve criar aluno com DTO informado e verificar chamada ao mock")
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

        verify(alunoService, times(1)).criarAluno(requestCaptor.capture());
        assertEquals("Premium", requestCaptor.getValue().getAssinaturaBase());
    }

    @Test
    @DisplayName("POST /api/alunos - Deve criar aluno com requestDTO nulo repassando DTO vazio default")
    void deveCriarAlunoComRequestNulo() {
        AlunoResponseDTO responseDTO = new AlunoResponseDTO();
        responseDTO.setId(2L);
        responseDTO.setAssinatura("Básica");

        when(alunoService.criarAluno(any(AlunoRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<AlunoResponseDTO> response = alunoController.criarAluno(null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getId());

        verify(alunoService, times(1)).criarAluno(requestCaptor.capture());
        assertNull(requestCaptor.getValue().getAssinaturaBase());
    }

    @Test
    @DisplayName("POST /api/alunos/{id}/conquistar-curso - Deve retornar 200 OK no sucesso via mock")
    void deveRetornarOkAoConquistarCurso() {
        AlunoResponseDTO responseDTO = new AlunoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setCursosConquistados(12);

        when(alunoService.conquistarCurso(1L)).thenReturn(responseDTO);

        ResponseEntity<?> response = alunoController.conquistarCurso(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(alunoService, times(1)).conquistarCurso(1L);
    }

    @Test
    @DisplayName("POST /api/alunos/{id}/conquistar-curso - Deve retornar 404 Not Found quando serviço lança exceção")
    void deveRetornarNotFoundAoConquistarCursoInexistente() {
        when(alunoService.conquistarCurso(99L)).thenThrow(new RuntimeException("Aluno não encontrado"));

        ResponseEntity<?> response = alunoController.conquistarCurso(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Aluno não encontrado", response.getBody());
        verify(alunoService, times(1)).conquistarCurso(99L);
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
        verify(alunoService, times(1)).resgatarVoucher(1L);
    }

    @Test
    @DisplayName("POST /api/alunos/{id}/resgatar-voucher - Deve retornar 400 Bad Request em caso de exceção no mock")
    void deveRetornarBadRequestAoFalharResgateDeVoucher() {
        when(alunoService.resgatarVoucher(1L)).thenThrow(new RuntimeException("Sem saldo"));

        ResponseEntity<?> response = alunoController.resgatarVoucher(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sem saldo", response.getBody());
        verify(alunoService, times(1)).resgatarVoucher(1L);
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
        verify(alunoService, times(1)).buscarAluno(1L);
    }

    @Test
    @DisplayName("GET /api/alunos/{id} - Deve retornar 404 Not Found quando aluno não existe")
    void deveRetornarNotFoundAoBuscarAlunoInexistente() {
        when(alunoService.buscarAluno(99L)).thenThrow(new RuntimeException("Aluno não encontrado"));

        ResponseEntity<?> response = alunoController.buscarAluno(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Aluno não encontrado", response.getBody());
        verify(alunoService, times(1)).buscarAluno(99L);
    }
}
