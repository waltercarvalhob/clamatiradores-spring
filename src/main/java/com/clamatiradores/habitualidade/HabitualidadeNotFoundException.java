package com.clamatiradores.habitualidade;

import com.clamatiradores.common.NotFoundException;

public class HabitualidadeNotFoundException extends NotFoundException {

	public HabitualidadeNotFoundException(Integer id) {
		super("Habitualidade nao encontrada: id_freq=" + id);
	}

}
