package com.clamatiradores.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clamatiradores.security.dto.UsuarioForm;

public interface UsuarioService {

	Page<Usuario> search(String username, Pageable pageable);

	Usuario findById(Integer id);

	Usuario create(UsuarioForm form);

	Usuario update(Integer id, UsuarioForm form, String currentUsername);

	void delete(Integer id, String currentUsername);

}
