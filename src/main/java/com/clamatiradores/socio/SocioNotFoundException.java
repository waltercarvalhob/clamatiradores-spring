package com.clamatiradores.socio;

import com.clamatiradores.common.NotFoundException;

public class SocioNotFoundException extends NotFoundException {

	public SocioNotFoundException(Integer id) {
		super("Socio nao encontrado: id_socio=" + id);
	}

}
