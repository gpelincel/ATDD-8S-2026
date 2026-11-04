package com.gabrielpelincel.attd_project.shared.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testes de Exception Handler Global: ApiExceptionHandler")
public class ApiExceptionHandlerTest {

    private ApiExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new ApiExceptionHandler();
    }

    @Test
    @DisplayName("Deve tratar RuntimeException e retornar HTTP 400 Bad Request com mensagem")
    void deveTratarRuntimeException() {
        RuntimeException ex = new RuntimeException("Erro inesperado de teste");

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleRuntime(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Erro inesperado de teste", response.getBody().get("erro"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException e mapear os erros de validação de campos")
    void deveTratarMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError1 = new FieldError("aluno", "nome", "Nome é obrigatório");
        FieldError fieldError2 = new FieldError("aluno", "email", "E-mail inválido");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertNotNull(response.getBody().get("timestamp"));

        @SuppressWarnings("unchecked")
        Map<String, String> erros = (Map<String, String>) response.getBody().get("erros");
        assertNotNull(erros);
        assertEquals(2, erros.size());
        assertEquals("Nome é obrigatório", erros.get("nome"));
        assertEquals("E-mail inválido", erros.get("email"));
    }
}
