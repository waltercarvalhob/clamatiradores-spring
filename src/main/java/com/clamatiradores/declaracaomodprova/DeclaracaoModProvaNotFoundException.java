package com.clamatiradores.declaracaomodprova;

import com.clamatiradores.common.NotFoundException;

public class DeclaracaoModProvaNotFoundException extends NotFoundException {

	public DeclaracaoModProvaNotFoundException(Integer id) {
		super("Declaracao de Modalidade de Prova nao encontrada: id_decmodprova=" + id);
	}

}
