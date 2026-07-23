package com.clamatiradores.habitualidade;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class HabitualidadeSpecifications {

	private HabitualidadeSpecifications() {
	}

	public static Specification<Habitualidade> fromCriteria(String nome, String cpf) {
		Specification<Habitualidade> spec = Specification.unrestricted();
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
