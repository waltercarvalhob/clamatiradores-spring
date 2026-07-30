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
	 * que ja existia no SQL legado). Ordenado pela distancia ate hoje (o mais proximo da
	 * data atual primeiro, seja um vencimento recente ou um vencimento proximo), em vez de
	 * simplesmente do vencido mais antigo pra frente - assim o que precisa de atencao
	 * imediata nao fica enterrado atras de uma divida antiga de inicio de ano.
	 */
	@Query(value = "SELECT * FROM socio WHERE validade LIKE CONCAT('%/', :ano) "
			+ "AND CHAR_LENGTH(validade) = 10 "
			+ "AND TO_DATE(validade, 'DD/MM/YYYY') < now() + INTERVAL '10 days' "
			+ "AND (:nome IS NULL OR nome ILIKE CONCAT('%', :nome, '%')) "
			+ "ORDER BY ABS(TO_DATE(validade, 'DD/MM/YYYY') - CURRENT_DATE) ASC",
			countQuery = "SELECT count(*) FROM socio WHERE validade LIKE CONCAT('%/', :ano) "
					+ "AND CHAR_LENGTH(validade) = 10 "
					+ "AND TO_DATE(validade, 'DD/MM/YYYY') < now() + INTERVAL '10 days' "
					+ "AND (:nome IS NULL OR nome ILIKE CONCAT('%', :nome, '%'))",
			nativeQuery = true)
	Page<Socio> findVencimentos(@Param("ano") String ano, @Param("nome") String nome, Pageable pageable);

}
