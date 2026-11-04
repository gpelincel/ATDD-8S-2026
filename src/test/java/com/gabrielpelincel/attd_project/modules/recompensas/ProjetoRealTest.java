package com.gabrielpelincel.attd_project.modules.recompensas;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealRequestDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Domínio e DTOs: ProjetoReal")
public class ProjetoRealTest {

    @Test
    @DisplayName("Deve testar entidade ProjetoReal com construtores, métodos e getters/setters")
    void deveTestarProjetoRealEntidade() {
        ProjetoReal p1 = new ProjetoReal();
        assertNull(p1.getId());
        assertNull(p1.getNome());
        assertFalse(p1.isVoucherAplicado());

        p1.setId(10L);
        p1.setNome("Projeto Inovação");
        p1.setVoucherAplicado(true);

        assertEquals(10L, p1.getId());
        assertEquals("Projeto Inovação", p1.getNome());
        assertTrue(p1.isVoucherAplicado());

        ProjetoReal p2 = new ProjetoReal("Projeto Alpha");
        assertEquals("Projeto Alpha", p2.getNome());
        assertFalse(p2.isVoucherAplicado());

        p2.aplicarVoucher();
        assertTrue(p2.isVoucherAplicado());
    }

    @Test
    @DisplayName("Deve testar ProjetoRealRequestDTO")
    void deveTestarProjetoRealRequestDTO() {
        ProjetoRealRequestDTO dtoVazio = new ProjetoRealRequestDTO();
        assertNull(dtoVazio.getNome());

        dtoVazio.setNome("Projeto Beta");
        assertEquals("Projeto Beta", dtoVazio.getNome());

        ProjetoRealRequestDTO dtoComParametro = new ProjetoRealRequestDTO("Projeto Gama");
        assertEquals("Projeto Gama", dtoComParametro.getNome());
    }

    @Test
    @DisplayName("Deve testar ProjetoRealResponseDTO a partir de entidade e com getters/setters")
    void deveTestarProjetoRealResponseDTO() {
        ProjetoReal projeto = new ProjetoReal("Projeto Delta");
        projeto.setId(100L);
        projeto.aplicarVoucher();

        ProjetoRealResponseDTO responseDTO = new ProjetoRealResponseDTO(projeto);
        assertEquals(100L, responseDTO.getId());
        assertEquals("Projeto Delta", responseDTO.getNome());
        assertTrue(responseDTO.isVoucherAplicado());

        ProjetoRealResponseDTO dtoVazio = new ProjetoRealResponseDTO();
        dtoVazio.setId(200L);
        dtoVazio.setNome("Projeto Ômega");
        dtoVazio.setVoucherAplicado(false);

        assertEquals(200L, dtoVazio.getId());
        assertEquals("Projeto Ômega", dtoVazio.getNome());
        assertFalse(dtoVazio.isVoucherAplicado());
    }
}
