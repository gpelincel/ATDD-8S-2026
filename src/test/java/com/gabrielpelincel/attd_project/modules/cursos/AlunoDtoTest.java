package com.gabrielpelincel.attd_project.modules.cursos;

import com.gabrielpelincel.attd_project.modules.cursos.domain.Aluno;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de DTO: AlunoRequestDTO e AlunoResponseDTO")
public class AlunoDtoTest {

    @Test
    @DisplayName("Deve testar construtores, getters e setters de AlunoRequestDTO")
    void deveTestarAlunoRequestDTO() {
        AlunoRequestDTO dtoPadrao = new AlunoRequestDTO();
        assertNull(dtoPadrao.getAssinaturaBase());

        dtoPadrao.setAssinaturaBase("Básica");
        assertEquals("Básica", dtoPadrao.getAssinaturaBase());

        AlunoRequestDTO dtoComParametro = new AlunoRequestDTO("Premium");
        assertEquals("Premium", dtoComParametro.getAssinaturaBase());
    }

    @Test
    @DisplayName("Deve testar construtor a partir da entidade Aluno em AlunoResponseDTO")
    void deveTestarAlunoResponseDTOComEntidade() {
        Aluno aluno = new Aluno();
        aluno.setId(10L);
        aluno.setAssinatura("Premium");
        aluno.setCursosConquistados(12);
        aluno.setMoedas(3);
        aluno.setVouchers(1);
        aluno.setAcessoVouchers(true);
        aluno.setAcessoProjetosReais(true);

        AlunoResponseDTO dto = new AlunoResponseDTO(aluno);

        assertEquals(10L, dto.getId());
        assertEquals("Premium", dto.getAssinatura());
        assertEquals(12, dto.getCursosConquistados());
        assertEquals(3, dto.getMoedas());
        assertEquals(1, dto.getVouchers());
        assertTrue(dto.isAcessoVouchers());
        assertTrue(dto.isAcessoProjetosReais());
    }

    @Test
    @DisplayName("Deve testar construtor padrão, getters e setters de AlunoResponseDTO")
    void deveTestarAlunoResponseDTOGettersSetters() {
        AlunoResponseDTO dto = new AlunoResponseDTO();
        dto.setId(20L);
        dto.setAssinatura("Básica");
        dto.setCursosConquistados(5);
        dto.setMoedas(2);
        dto.setVouchers(0);
        dto.setAcessoVouchers(false);
        dto.setAcessoProjetosReais(false);

        assertEquals(20L, dto.getId());
        assertEquals("Básica", dto.getAssinatura());
        assertEquals(5, dto.getCursosConquistados());
        assertEquals(2, dto.getMoedas());
        assertEquals(0, dto.getVouchers());
        assertFalse(dto.isAcessoVouchers());
        assertFalse(dto.isAcessoProjetosReais());
    }
}
