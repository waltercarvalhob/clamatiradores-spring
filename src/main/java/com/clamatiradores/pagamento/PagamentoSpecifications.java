package com.clamatiradores.pagamento;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class PagamentoSpecifications {

	private PagamentoSpecifications() {
	}

	public static Specification<Pagamento> socioNomeContains(String nome) {
		if (!StringUtils.hasText(nome)) {
			return null;
		}
		String pattern = "%" + nome.trim().toUpperCase() + "%";
		return (root, query, cb) -> cb.like(cb.upper(root.get("socio").get("nome")), pattern);
	}

	public static Specification<Pagamento> socioCpfContains(String cpf) {
		if (!StringUtils.hasText(cpf)) {
			return null;
		}
		String pattern = "%" + cpf.trim() + "%";
		return (root, query, cb) -> cb.like(root.get("socio").get("cpf"), pattern);
	}

	public static Specification<Pagamento> fromCriteria(String nome, String cpf) {
		Specification<Pagamento> spec = Specification.unrestricted();
		Specification<Pagamento> nomeSpec = socioNomeContains(nome);
		Specification<Pagamento> cpfSpec = socioCpfContains(cpf);
		if (nomeSpec != null) {
			spec = spec.and(nomeSpec);
		}
		if (cpfSpec != null) {
			spec = spec.and(cpfSpec);
		}
		return spec;
	}

}
