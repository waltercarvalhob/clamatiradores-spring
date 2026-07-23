package com.clamatiradores.declaracaohab;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class DeclaracaohabSpecifications {

	private DeclaracaohabSpecifications() {
	}

	public static Specification<Declaracaohab> fromCriteria(String nome, String cpf) {
		Specification<Declaracaohab> spec = Specification.unrestricted();
		if (StringUtils.hasText(nome)) {
			String pattern = "%" + nome.trim().toUpperCase() + "%";
			spec = spec.and((root, query, cb) -> cb.like(cb.upper(root.get("socio").get("nome")), pattern));
		}
		if (StringUtils.hasText(cpf)) {
			String pattern = "%" + cpf.trim() + "%";
			spec = spec.and((root, query, cb) -> cb.like(root.get("socio").get("cpf"), pattern));
		}
		return spec;
	}

}
