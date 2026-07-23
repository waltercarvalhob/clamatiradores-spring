package com.clamatiradores.declaracao;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class DeclaracaoSpecifications {

	private DeclaracaoSpecifications() {
	}

	public static Specification<Declaracao> fromCriteria(String nome, String cpf) {
		Specification<Declaracao> spec = Specification.unrestricted();
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
