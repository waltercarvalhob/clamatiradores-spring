package com.clamatiradores.socio;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.clamatiradores.socio.dto.SocioSearchCriteria;

/**
 * Substitui a filtragem em memoria + paginacao manual do consultasocio.jsp legado
 * (que fazia "select * from socio" e filtrava linha a linha em Java) por filtros
 * reais em SQL via Specification, combinados com Pageable no repositorio.
 */
public final class SocioSpecifications {

	private SocioSpecifications() {
	}

	public static Specification<Socio> nomeContains(String nome) {
		if (!StringUtils.hasText(nome)) {
			return null;
		}
		String pattern = "%" + nome.trim().toUpperCase() + "%";
		return (root, query, cb) -> cb.like(cb.upper(root.get("nome")), pattern);
	}

	public static Specification<Socio> cpfContains(String cpf) {
		if (!StringUtils.hasText(cpf)) {
			return null;
		}
		String pattern = "%" + cpf.trim() + "%";
		return (root, query, cb) -> cb.like(root.get("cpf"), pattern);
	}

	public static Specification<Socio> numcrContains(String numcr) {
		if (!StringUtils.hasText(numcr)) {
			return null;
		}
		String pattern = "%" + numcr.trim().toUpperCase() + "%";
		return (root, query, cb) -> cb.like(cb.upper(root.get("numcr")), pattern);
	}

	public static Specification<Socio> datanascStartsWith(String datanasc) {
		if (!StringUtils.hasText(datanasc)) {
			return null;
		}
		String pattern = datanasc.trim() + "%";
		return (root, query, cb) -> cb.like(root.get("datanasc"), pattern);
	}

	public static Specification<Socio> fromCriteria(SocioSearchCriteria criteria) {
		Specification<Socio> spec = Specification.unrestricted();

		Specification<Socio> nome = nomeContains(criteria.getNome());
		Specification<Socio> cpf = cpfContains(criteria.getCpf());
		Specification<Socio> numcr = numcrContains(criteria.getNumcr());
		Specification<Socio> datanasc = datanascStartsWith(criteria.getDatanasc());

		if (nome != null) {
			spec = spec.and(nome);
		}
		if (cpf != null) {
			spec = spec.and(cpf);
		}
		if (numcr != null) {
			spec = spec.and(numcr);
		}
		if (datanasc != null) {
			spec = spec.and(datanasc);
		}
		return spec;
	}

}
