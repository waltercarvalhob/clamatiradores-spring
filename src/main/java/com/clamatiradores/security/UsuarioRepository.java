package com.clamatiradores.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {

	Optional<Usuario> findByUsername(String username);

	long countByRoleAndEnabledTrueAndIdUsuarioNot(String role, Integer idUsuario);

}
