package com.clamatiradores.declaracaomodprova;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class DeclaracaoModProvaSpecifications {

	private DeclaracaoModProvaSpecifications() {
	}

	public static Specification<DeclaracaoModProva> fromCriteria(String nome, String cpf) {
		Specification<DeclaracaoModProva> spec = Specification.unrestricted();
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
