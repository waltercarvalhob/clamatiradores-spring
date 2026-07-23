package com.clamatiradores.habitualidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clamatiradores.habitualidade.dto.HabitualidadeForm;

public interface HabitualidadeService {

	Page<Habitualidade> search(String nome, String cpf, Pageable pageable);

	Habitualidade findById(Integer id);

	Habitualidade create(HabitualidadeForm form);

	Habitualidade update(Integer id, HabitualidadeForm form);

	void delete(Integer id);

}
