package com.gabrielpelincel.attd_project.modules.recompensas;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealRequestDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealResponseDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.repository.ProjetoRealRepository;
import com.gabrielpelincel.attd_project.modules.recompensas.service.ProjetoRealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Serviço: ProjetoRealService")
public class ProjetoRealServiceTest {

    @Mock
    private ProjetoRealRepository projetoRepository;

    private ProjetoRealService projetoRealService;

    @BeforeEach
    void setUp() {
        projetoRealService = new ProjetoRealService(projetoRepository);
    }

    @Test
    @DisplayName("Deve criar um novo projeto real com sucesso")
    void deveCriarProjetoComSucesso() {
        ProjetoRealRequestDTO request = new ProjetoRealRequestDTO("App Mobile Gamificado");

        when(projetoRepository.save(any(ProjetoReal.class))).thenAnswer(invocation -> {
            ProjetoReal p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        ProjetoRealResponseDTO response = projetoRealService.criarProjeto(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("App Mobile Gamificado", response.getNome());
        assertFalse(response.isVoucherAplicado());
        verify(projetoRepository).save(any(ProjetoReal.class));
    }

    @Test
    @DisplayName("Deve listar todos os projetos reais cadastrados")
    void deveListarProjetosComSucesso() {
        ProjetoReal p1 = new ProjetoReal("Projeto A");
        p1.setId(1L);
        ProjetoReal p2 = new ProjetoReal("Projeto B");
        p2.setId(2L);

        when(projetoRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProjetoRealResponseDTO> lista = projetoRealService.listarProjetos();

        assertEquals(2, lista.size());
        assertEquals("Projeto A", lista.get(0).getNome());
        assertEquals("Projeto B", lista.get(1).getNome());
        verify(projetoRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma nova instância de ProjetoReal quando idProjeto for nulo")
    void deveRetornarNovoProjetoQuandoIdForNulo() {
        ProjetoReal resultado = projetoRealService.buscarPorIdOuNovo(null);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertFalse(resultado.isVoucherAplicado());
        verify(projetoRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Deve retornar projeto existente quando idProjeto for encontrado")
    void deveRetornarProjetoExistenteQuandoEncontrado() {
        ProjetoReal projeto = new ProjetoReal("Projeto Especial");
        projeto.setId(10L);

        when(projetoRepository.findById(10L)).thenReturn(Optional.of(projeto));

        ProjetoReal resultado = projetoRealService.buscarPorIdOuNovo(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Projeto Especial", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar projeto inexistente por ID")
    void deveLancarExcecaoAoBuscarProjetoInexistente() {
        when(projetoRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> projetoRealService.buscarPorIdOuNovo(999L));
        assertEquals("Projeto Real não encontrado com o ID: 999", ex.getMessage());
    }

    @Test
    @DisplayName("Deve salvar entidade ProjetoReal diretamente")
    void deveSalvarProjetoReal() {
        ProjetoReal projeto = new ProjetoReal("Projeto Salvar");
        when(projetoRepository.save(projeto)).thenReturn(projeto);

        ProjetoReal salvo = projetoRealService.salvar(projeto);

        assertSame(projeto, salvo);
        verify(projetoRepository).save(projeto);
    }
}
