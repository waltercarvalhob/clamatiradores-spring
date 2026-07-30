package com.clamatiradores.security;

import com.clamatiradores.common.NotFoundException;

public class UsuarioNotFoundException extends NotFoundException {

	public UsuarioNotFoundException(Integer id) {
		super("Usuario nao encontrado: id_usuario=" + id);
	}

}
