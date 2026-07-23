package com.clamatiradores.declaracao;

import com.clamatiradores.common.NotFoundException;

public class DeclaracaoNotFoundException extends NotFoundException {

	public DeclaracaoNotFoundException(Integer id) {
		super("Declaracao nao encontrada: id_dec=" + id);
	}

}
