package com.clamatiradores.socio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SocioRepository extends JpaRepository<Socio, Integer>, JpaSpecificationExecutor<Socio> {

	boolean existsByCpf(String cpf);

	/**
	 * Reproduz a consulta das paginas legadas VencimentoPorNome*.jsp/VencimentoPorData*.jsp
	 * (uma pagina duplicada por ano no menu antigo): socios cuja validade cai no ano
	 * informado e que ja venceu ou vence nos proximos 10 dias, ordenados pela data de
	 * validade. O CHAR_LENGTH(validade) = 10 descarta valores que nao estao no formato
	 * DD/MM/YYYY (protecao de qualidade de dado que ja existia no SQL legado).
	 */
	@Query(value = "SELECT * FROM socio WHERE validade LIKE CONCAT('%/', :ano) "
			+ "AND CHAR_LENGTH(validade) = 10 "
			+ "AND TO_DATE(validade, 'DD/MM/YYYY') < now() + INTERVAL '10 days' "
			+ "AND (:nome IS NULL OR nome ILIKE CONCAT('%', :nome, '%')) "
			+ "ORDER BY TO_DATE(validade, 'DD/MM/YYYY') ASC", nativeQuery = true)
	List<Socio> findVencimentos(@Param("ano") String ano, @Param("nome") String nome);

}
