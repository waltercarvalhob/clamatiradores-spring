package com.clamatiradores.security;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class UsuarioSpecifications {

	private UsuarioSpecifications() {
	}

	public static Specification<Usuario> usernameContains(String username) {
		if (!StringUtils.hasText(username)) {
			return null;
		}
		String pattern = "%" + username.trim().toUpperCase() + "%";
		return (root, query, cb) -> cb.like(cb.upper(root.get("username")), pattern);
	}

	public static Specification<Usuario> fromCriteria(String username) {
		Specification<Usuario> spec = Specification.unrestricted();
		Specification<Usuario> usernameSpec = usernameContains(username);
		if (usernameSpec != null) {
			spec = spec.and(usernameSpec);
		}
		return spec;
	}

}
