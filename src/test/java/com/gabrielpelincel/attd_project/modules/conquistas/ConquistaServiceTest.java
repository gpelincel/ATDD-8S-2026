package com.gabrielpelincel.attd_project.modules.conquistas;

import com.gabrielpelincel.attd_project.modules.conquistas.service.ConquistaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Serviço: ConquistaService")
public class ConquistaServiceTest {

    private ConquistaService conquistaService;

    @BeforeEach
    void setUp() {
        conquistaService = new ConquistaService();
    }

    @Test
    @DisplayName("Não deve ser elegível para Premium com menos de 12 cursos")
    void naoDeveSerElegivelParaPremiumComMenosDeDozeCursos() {
        assertFalse(conquistaService.isElegivelParaPremium(11, ConquistaService.ASSINATURA_BASICA));
        assertFalse(conquistaService.isElegivelParaPremium(0, ConquistaService.ASSINATURA_BASICA));
    }

    @Test
    @DisplayName("Deve ser elegível para Premium com 12 ou mais cursos se for Básica")
    void deveSerElegivelParaPremiumComDozeOuMaisCursosSeForBasica() {
        assertTrue(conquistaService.isElegivelParaPremium(12, ConquistaService.ASSINATURA_BASICA));
        assertTrue(conquistaService.isElegivelParaPremium(15, ConquistaService.ASSINATURA_BASICA));
    }

    @Test
    @DisplayName("Não deve ser elegível para Premium se já for Premium")
    void naoDeveSerElegivelParaPremiumSeJaForPremium() {
        assertFalse(conquistaService.isElegivelParaPremium(12, ConquistaService.ASSINATURA_PREMIUM));
        assertFalse(conquistaService.isElegivelParaPremium(20, ConquistaService.ASSINATURA_PREMIUM));
    }

    @Test
    @DisplayName("Deve validar constantes de negócio do módulo Conquistas")
    void deveValidarConstantesDeNegocio() {
        assertEquals(12, ConquistaService.META_CURSOS_PREMIUM);
        assertEquals("Básica", ConquistaService.ASSINATURA_BASICA);
        assertEquals("Premium", ConquistaService.ASSINATURA_PREMIUM);
        assertEquals(3, ConquistaService.MOEDAS_BONUS_PREMIUM);
        assertEquals(1, ConquistaService.VOUCHERS_BONUS_PREMIUM);
    }
}
