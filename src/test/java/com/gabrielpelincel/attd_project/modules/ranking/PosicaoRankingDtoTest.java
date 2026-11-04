package com.gabrielpelincel.attd_project.modules.ranking;

import com.gabrielpelincel.attd_project.modules.ranking.dto.PosicaoRankingDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de DTO: PosicaoRankingDTO")
public class PosicaoRankingDtoTest {

    @Test
    @DisplayName("Deve testar construtor completo e getters")
    void deveTestarConstrutorCompleto() {
        PosicaoRankingDTO dto = new PosicaoRankingDTO(1, 10L, "Premium", 15, 6);

        assertEquals(1, dto.getPosicao());
        assertEquals(10L, dto.getAlunoId());
        assertEquals("Premium", dto.getAssinatura());
        assertEquals(15, dto.getCursosConquistados());
        assertEquals(6, dto.getMoedas());
    }

    @Test
    @DisplayName("Deve testar construtor padrão e setters")
    void deveTestarConstrutorPadraoESetters() {
        PosicaoRankingDTO dto = new PosicaoRankingDTO();
        dto.setPosicao(2);
        dto.setAlunoId(20L);
        dto.setAssinatura("Básica");
        dto.setCursosConquistados(11);
        dto.setMoedas(0);

        assertEquals(2, dto.getPosicao());
        assertEquals(20L, dto.getAlunoId());
        assertEquals("Básica", dto.getAssinatura());
        assertEquals(11, dto.getCursosConquistados());
        assertEquals(0, dto.getMoedas());
    }
}
