package com.gabrielpelincel.attd_project.modules.cursos;

import com.gabrielpelincel.attd_project.modules.cursos.domain.Aluno;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoResponseDTO;
import com.gabrielpelincel.attd_project.modules.cursos.repository.AlunoRepository;
import com.gabrielpelincel.attd_project.modules.cursos.service.AlunoService;
import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import com.gabrielpelincel.attd_project.modules.recompensas.service.RecompensaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Serviço: AlunoService (com Mockito @Mock e @InjectMocks)")
public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private RecompensaService recompensaService;

    @InjectMocks
    private AlunoService alunoService;

    @Captor
    private ArgumentCaptor<Aluno> alunoCaptor;

    @Captor
    private ArgumentCaptor<ProjetoReal> projetoCaptor;

    @Test
    @DisplayName("Deve buscar aluno por ID com sucesso utilizando Mockito when/verify")
    void deveBuscarAlunoPorIdComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setAssinatura("Básica");
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        AlunoResponseDTO response = alunoService.buscarAluno(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Básica", response.getAssinatura());
        verify(alunoRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(alunoRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar aluno com ID inexistente")
    void deveLancarExcecaoAoBuscarAlunoInexistente() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.buscarAluno(99L));
        assertEquals("Aluno não encontrado com o ID: 99", ex.getMessage());
        verify(alunoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Deve criar aluno com requestDTO nulo usando assinatura padrão capturada por ArgumentCaptor")
    void deveCriarAlunoComRequestNulo() {
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponseDTO response = alunoService.criarAluno(null);

        assertNotNull(response);
        assertEquals("Básica", response.getAssinatura());

        verify(alunoRepository, times(1)).save(alunoCaptor.capture());
        Aluno capturado = alunoCaptor.getValue();
        assertEquals("Básica", capturado.getAssinatura());
        assertEquals(0, capturado.getCursosConquistados());
    }

    @Test
    @DisplayName("Deve criar aluno quando assinaturaBase for nula ou em branco")
    void deveCriarAlunoComAssinaturaNulaOuEmBranco() {
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponseDTO resNulo = alunoService.criarAluno(new AlunoRequestDTO(null));
        assertEquals("Básica", resNulo.getAssinatura());

        AlunoResponseDTO resVazio = alunoService.criarAluno(new AlunoRequestDTO("   "));
        assertEquals("Básica", resVazio.getAssinatura());

        verify(alunoRepository, times(2)).save(any(Aluno.class));
    }

    @Test
    @DisplayName("Deve criar aluno com assinatura personalizada informada validada pelo ArgumentCaptor")
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

        verify(alunoRepository).save(alunoCaptor.capture());
        assertEquals("Premium", alunoCaptor.getValue().getAssinatura());
    }

    @Test
    @DisplayName("Deve conquistar curso com sucesso e persistir estado via Mockito")
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

        verify(alunoRepository, times(1)).findById(1L);
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    @DisplayName("Deve lançar exceção ao conquistar curso de aluno inexistente sem salvar no repositório")
    void deveLancarExcecaoAoConquistarCursoDeAlunoInexistente() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.conquistarCurso(99L));
        assertEquals("Aluno não encontrado com o ID: 99", ex.getMessage());

        verify(alunoRepository, times(1)).findById(99L);
        verify(alunoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve resgatar voucher com sucesso quando aluno tem saldo delegando ao RecompensaService mockado")
    void deveResgatarVoucherComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setId(2L);
        aluno.setVouchers(1);

        when(alunoRepository.findById(2L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AlunoResponseDTO response = alunoService.resgatarVoucher(2L);

        assertNotNull(response);
        assertEquals(0, response.getVouchers());

        verify(recompensaService, times(1)).resgatarVoucherEmProjeto(eq(1), projetoCaptor.capture());
        assertNotNull(projetoCaptor.getValue());
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    @DisplayName("Deve lançar exceção ao resgatar voucher de aluno inexistente sem acionar serviço de recompensas")
    void deveLancarExcecaoAoResgatarVoucherDeAlunoInexistente() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.resgatarVoucher(99L));
        assertEquals("Aluno não encontrado com o ID: 99", ex.getMessage());

        verify(alunoRepository, times(1)).findById(99L);
        verifyNoInteractions(recompensaService);
    }

    @Test
    @DisplayName("Deve lançar exceção ao resgatar voucher quando saldo for zero ou negativo e nunca acionar recompensaService")
    void deveLancarExcecaoAoResgatarVoucherSemSaldo() {
        Aluno aluno = new Aluno();
        aluno.setId(3L);
        aluno.setVouchers(0);

        when(alunoRepository.findById(3L)).thenReturn(Optional.of(aluno));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> alunoService.resgatarVoucher(3L));
        assertEquals("Aluno não possui saldo de vouchers disponível para resgate.", ex.getMessage());

        verify(recompensaService, never()).resgatarVoucherEmProjeto(anyInt(), any());
        verify(alunoRepository, never()).save(any());
    }
}
