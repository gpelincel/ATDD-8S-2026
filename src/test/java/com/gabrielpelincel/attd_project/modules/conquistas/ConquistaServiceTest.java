package com.gabrielpelincel.attd_project.modules.conquistas;

import com.gabrielpelincel.attd_project.modules.conquistas.service.ConquistaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConquistaServiceTest {

    private ConquistaService conquistaService;

    @BeforeEach
    void setUp() {
        conquistaService = new ConquistaService();
    }

    @Test
    void naoDeveSerElegivelParaPremiumComMenosDeDozeCursos() {
        assertFalse(conquistaService.isElegivelParaPremium(11, ConquistaService.ASSINATURA_BASICA));
        assertFalse(conquistaService.isElegivelParaPremium(0, ConquistaService.ASSINATURA_BASICA));
    }

    @Test
    void deveSerElegivelParaPremiumComDozeOuMaisCursosSeForBasica() {
        assertTrue(conquistaService.isElegivelParaPremium(12, ConquistaService.ASSINATURA_BASICA));
        assertTrue(conquistaService.isElegivelParaPremium(15, ConquistaService.ASSINATURA_BASICA));
    }

    @Test
    void naoDeveSerElegivelParaPremiumSeJaForPremium() {
        assertFalse(conquistaService.isElegivelParaPremium(12, ConquistaService.ASSINATURA_PREMIUM));
        assertFalse(conquistaService.isElegivelParaPremium(20, ConquistaService.ASSINATURA_PREMIUM));
    }
}
