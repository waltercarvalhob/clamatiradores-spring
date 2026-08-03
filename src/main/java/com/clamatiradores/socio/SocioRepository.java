package com.clamatiradores.socio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SocioRepository extends JpaRepository<Socio, Integer>, JpaSpecificationExecutor<Socio> {

	boolean existsByCpf(String cpf);

	/**
	 * Reproduz a consulta das paginas legadas VencimentoPorNome*.jsp/VencimentoPorData*.jsp
	 * (uma pagina duplicada por ano no menu antigo): socios cuja validade cai no ano
	 * informado e que ja venceu ou vence nos proximos 10 dias. O CHAR_LENGTH(validade) = 10
	 * descarta valores que nao estao no formato DD/MM/YYYY (protecao de qualidade de dado
	 * que ja existia no SQL legado); safe_to_date() (ver SafeToDateFunctionInitializer)
	 * descarta o resto - datas com o formato certo mas invalidas no calendario, como
	 * "31/04/2024" - que passariam pelo CHAR_LENGTH mas fariam um TO_DATE comum lancar
	 * excecao. Ordenado pela distancia ate hoje (o mais proximo da data atual primeiro,
	 * seja um vencimento recente ou um vencimento proximo), em vez de simplesmente do
	 * vencido mais antigo pra frente - assim o que precisa de atencao imediata nao fica
	 * enterrado atras de uma divida antiga de inicio de ano.
	 */
	@Query(value = "SELECT * FROM socio WHERE validade LIKE CONCAT('%/', :ano) "
			+ "AND CHAR_LENGTH(validade) = 10 "
			+ "AND safe_to_date(validade, 'DD/MM/YYYY') IS NOT NULL "
			+ "AND safe_to_date(validade, 'DD/MM/YYYY') < now() + INTERVAL '10 days' "
			+ "AND (:nome IS NULL OR nome ILIKE CONCAT('%', :nome, '%')) "
			+ "AND (:mes IS NULL OR SUBSTRING(validade, 4, 2) = :mes) "
			+ "AND (:dia IS NULL OR SUBSTRING(validade, 1, 2) = :dia) "
			+ "AND (:dataAbreviada IS NULL OR validade LIKE CONCAT('%', :dataAbreviada, '%')) "
			+ "ORDER BY ABS(safe_to_date(validade, 'DD/MM/YYYY') - CURRENT_DATE) ASC",
			countQuery = "SELECT count(*) FROM socio WHERE validade LIKE CONCAT('%/', :ano) "
					+ "AND CHAR_LENGTH(validade) = 10 "
					+ "AND safe_to_date(validade, 'DD/MM/YYYY') IS NOT NULL "
					+ "AND safe_to_date(validade, 'DD/MM/YYYY') < now() + INTERVAL '10 days' "
					+ "AND (:nome IS NULL OR nome ILIKE CONCAT('%', :nome, '%')) "
					+ "AND (:mes IS NULL OR SUBSTRING(validade, 4, 2) = :mes) "
					+ "AND (:dia IS NULL OR SUBSTRING(validade, 1, 2) = :dia) "
					+ "AND (:dataAbreviada IS NULL OR validade LIKE CONCAT('%', :dataAbreviada, '%'))",
			nativeQuery = true)
	Page<Socio> findVencimentos(@Param("ano") String ano, @Param("nome") String nome, @Param("mes") String mes,
			@Param("dia") String dia, @Param("dataAbreviada") String dataAbreviada, Pageable pageable);

}
