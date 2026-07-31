package com.clamatiradores.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Cria (ou substitui, se ja existir) a funcao safe_to_date no Postgres a cada
 * subida da aplicacao.
 *
 * A coluna socio.validade e um varchar legado (nao um DATE de verdade), e
 * mesmo com o filtro por regex `^\d{2}/\d{2}/\d{4}$` usado nas consultas,
 * datas com o formato certo mas o calendario invalido (ex.: "31/04/2024" -
 * abril so tem 30 dias, ou "29/02/2023" - 2023 nao e bissexto) passam pelo
 * regex mas fazem TO_DATE(...) do Postgres lancar excecao em tempo de
 * consulta, derrubando a tela/relatorio com erro 500. safe_to_date() envolve
 * TO_DATE num bloco de excecao e devolve NULL em vez de estourar, entao uma
 * linha com data de calendario invalida so fica de fora do resultado, sem
 * quebrar a pagina inteira.
 */
@Component
public class SafeToDateFunctionInitializer implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SafeToDateFunctionInitializer.class);

	private static final String DDL = """
			CREATE OR REPLACE FUNCTION safe_to_date(txt text, fmt text)
			RETURNS date
			LANGUAGE plpgsql
			IMMUTABLE
			AS $func$
			BEGIN
				RETURN to_date(txt, fmt);
			EXCEPTION WHEN OTHERS THEN
				RETURN NULL;
			END;
			$func$;
			""";

	private final JdbcTemplate jdbcTemplate;

	public SafeToDateFunctionInitializer(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) {
		jdbcTemplate.execute(DDL);
		log.info("Funcao safe_to_date() verificada/criada no banco.");
	}

}
