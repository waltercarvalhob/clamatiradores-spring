package com.clamatiradores.declaracaohab;

import com.clamatiradores.common.NotFoundException;

public class DeclaracaohabNotFoundException extends NotFoundException {

	public DeclaracaohabNotFoundException(Integer id) {
		super("Declaracao de Habitualidade nao encontrada: id_dec=" + id);
	}

}
