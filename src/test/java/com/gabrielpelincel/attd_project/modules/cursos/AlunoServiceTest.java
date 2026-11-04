package com.gabrielpelincel.attd_project.modules.cursos;

import com.gabrielpelincel.attd_project.modules.cursos.domain.Aluno;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoResponseDTO;
import com.gabrielpelincel.attd_project.modules.cursos.repository.AlunoRepository;
import com.gabrielpelincel.attd_project.modules.cursos.service.AlunoService;
import com.gabrielpelincel.attd_project.modules.recompensas.service.RecompensaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Serviço: AlunoService")
public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private RecompensaService recompensaService;

    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        alunoService = new AlunoService(alunoRepository, recompensaService);
    }

    @Test
    @DisplayName("Deve buscar aluno por ID com sucesso")
    void deveBuscarAlunoPorIdComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setAssinatura("Básica");
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        AlunoResponseDTO response = alunoService.buscarAluno(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Básica", response.getAssinatura());
        verify(alunoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar aluno com ID inexistente")
    void deveLancarExcecaoAoBuscarAlunoInexistente() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.buscarAluno(99L));
        assertEquals("Aluno não encontrado com o ID: 99", ex.getMessage());
    }

    @Test
    @DisplayName("Deve criar aluno com requestDTO nulo usando assinatura padrão")
    void deveCriarAlunoComRequestNulo() {
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponseDTO response = alunoService.criarAluno(null);

        assertNotNull(response);
        assertEquals("Básica", response.getAssinatura());
        verify(alunoRepository).save(any(Aluno.class));
    }

    @Test
    @DisplayName("Deve criar aluno quando assinaturaBase for nula ou em branco")
    void deveCriarAlunoComAssinaturaNulaOuEmBranco() {
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponseDTO resNulo = alunoService.criarAluno(new AlunoRequestDTO(null));
        assertEquals("Básica", resNulo.getAssinatura());

        AlunoResponseDTO resVazio = alunoService.criarAluno(new AlunoRequestDTO("   "));
        assertEquals("Básica", resVazio.getAssinatura());
    }

    @Test
    @DisplayName("Deve criar aluno com assinatura personalizada informada")
    void deveCriarAlunoComAssinaturaInformada() {
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> {
            Aluno salvo = invocation.getArgument(0);
            salvo.setId(5L);
            return salvo;
        });

        AlunoResponseDTO response = alunoService.criarAluno(new AlunoRequestDTO("Premium"));

        assertNotNull(response);
        assertEquals(5L, response.getId());
        assertEquals("Premium", response.getAssinatura());
    }

    @Test
    @DisplayName("Deve conquistar curso com sucesso")
    void deveConquistarCursoComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setCursosConquistados(11);
        aluno.setAssinatura("Básica");

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponseDTO response = alunoService.conquistarCurso(1L);

        assertEquals(12, response.getCursosConquistados());
        assertEquals("Premium", response.getAssinatura());
        assertEquals(3, response.getMoedas());
        verify(alunoRepository).save(aluno);
    }

    @Test
    @DisplayName("Deve lançar exceção ao conquistar curso de aluno inexistente")
    void deveLancarExcecaoAoConquistarCursoDeAlunoInexistente() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.conquistarCurso(99L));
        assertEquals("Aluno não encontrado com o ID: 99", ex.getMessage());
    }

    @Test
    @DisplayName("Deve resgatar voucher com sucesso quando aluno tem saldo")
    void deveResgatarVoucherComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setVouchers(1);

        when(alunoRepository.findById(2L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponseDTO response = alunoService.resgatarVoucher(2L);

        assertNotNull(response);
        assertEquals(0, response.getVouchers());
        verify(recompensaService).resgatarVoucherEmProjeto(eq(1), any());
        verify(alunoRepository).save(aluno);
    }

    @Test
    @DisplayName("Deve lançar exceção ao resgatar voucher de aluno inexistente")
    void deveLancarExcecaoAoResgatarVoucherDeAlunoInexistente() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.resgatarVoucher(99L));
        assertEquals("Aluno não encontrado com o ID: 99", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao resgatar voucher quando saldo for zero ou negativo")
    void deveLancarExcecaoAoResgatarVoucherSemSaldo() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setVouchers(0);

        when(alunoRepository.findById(3L)).thenReturn(Optional.of(aluno));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.resgatarVoucher(3L));
        assertEquals("Aluno não possui saldo de vouchers disponível para resgate.", ex.getMessage());
        verify(recompensaService, never()).resgatarVoucherEmProjeto(anyInt(), any());
    }
}
