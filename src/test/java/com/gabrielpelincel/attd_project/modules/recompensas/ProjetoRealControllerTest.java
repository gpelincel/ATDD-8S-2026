package com.gabrielpelincel.attd_project.modules.recompensas;

import com.gabrielpelincel.attd_project.modules.recompensas.controller.ProjetoRealController;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealRequestDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealResponseDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.service.ProjetoRealService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Controller: ProjetoRealController (com Mockito @Mock, @InjectMocks e ArgumentCaptor)")
public class ProjetoRealControllerTest {

    @Mock
    private ProjetoRealService projetoService;

    @InjectMocks
    private ProjetoRealController projetoRealController;

    @Captor
    private ArgumentCaptor<ProjetoRealRequestDTO> requestCaptor;

    @Test
    @DisplayName("POST /api/projetos - Deve criar projeto e verificar repasse ao mock")
    void deveCriarProjetoComSucesso() {
        ProjetoRealRequestDTO request = new ProjetoRealRequestDTO("Projeto Inovador");
        ProjetoRealResponseDTO responseDTO = new ProjetoRealResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Projeto Inovador");
        responseDTO.setVoucherAplicado(false);

        when(projetoService.criarProjeto(request)).thenReturn(responseDTO);

        ResponseEntity<ProjetoRealResponseDTO> response = projetoRealController.criarProjeto(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Projeto Inovador", response.getBody().getNome());

        verify(projetoService, times(1)).criarProjeto(requestCaptor.capture());
        assertEquals("Projeto Inovador", requestCaptor.getValue().getNome());
    }

    @Test
    @DisplayName("GET /api/projetos - Deve listar todos os projetos e retornar 200 OK via mock")
    void deveListarProjetosComSucesso() {
        ProjetoRealResponseDTO dto1 = new ProjetoRealResponseDTO();
        dto1.setId(1L);
        dto1.setNome("Projeto Alpha");

        ProjetoRealResponseDTO dto2 = new ProjetoRealResponseDTO();
        dto2.setId(2L);
        dto2.setNome("Projeto Beta");

        when(projetoService.listarProjetos()).thenReturn(List.of(dto1, dto2));

        ResponseEntity<List<ProjetoRealResponseDTO>> response = projetoRealController.listarProjetos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(projetoService, times(1)).listarProjetos();
    }
}
