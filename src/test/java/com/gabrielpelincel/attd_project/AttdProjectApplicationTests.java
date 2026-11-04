package com.gabrielpelincel.attd_project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Testes de Inicialização da Aplicação: AttdProjectApplication")
class AttdProjectApplicationTests {

	@Test
	@DisplayName("Deve carregar o contexto da aplicação com sucesso")
	void contextLoads() {
	}

	@Test
	@DisplayName("Deve inicializar a classe e executar o método main")
	void deveInstanciarEExecutarMain() {
		AttdProjectApplication application = new AttdProjectApplication();
		assertNotNull(application);

		try (MockedStatic<SpringApplication> mockedSpring = Mockito.mockStatic(SpringApplication.class)) {
			mockedSpring.when(() -> SpringApplication.run(AttdProjectApplication.class, new String[]{}))
					.thenReturn(null);

			AttdProjectApplication.main(new String[]{});

			mockedSpring.verify(() -> SpringApplication.run(AttdProjectApplication.class, new String[]{}));
		}
	}
}
