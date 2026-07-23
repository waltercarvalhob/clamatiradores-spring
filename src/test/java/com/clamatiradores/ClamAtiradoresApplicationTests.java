package com.clamatiradores;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * Testes de fumaca rodados no pipeline de CI (ver .github/workflows/ci.yml),
 * contra um Postgres real inicializado com db/demo_schema_seed.sql - isso
 * detecta automaticamente o tipo de bug que apareceu varias vezes durante a
 * migracao (mapeamento de entidade divergente do schema real, dependencia de
 * runtime faltando para os relatorios, etc), sem precisar rodar tudo na mao.
 *
 * Usa RestTestClient (substituiu o TestRestTemplate, removido no Spring Boot 4).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClamAtiradoresApplicationTests {

	@LocalServerPort
	private int port;

	private RestTestClient client() {
		return RestTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
	}

	@Test
	void contextLoads() {
		// Sobe o contexto completo (JPA validando as 7 entidades contra o schema
		// real do Postgres de teste, Spring Security, Thymeleaf) - se algo estiver
		// incompativel com o banco, o teste falha aqui.
	}

	@Test
	void paginaDeLoginEhPublica() {
		client().get().uri("/login").exchange().expectStatus().isOk();
	}

	@Test
	void listaDeSociosExigeAutenticacao() {
		client().get().uri("/socios").exchange().expectStatus().isFound();
	}

}
