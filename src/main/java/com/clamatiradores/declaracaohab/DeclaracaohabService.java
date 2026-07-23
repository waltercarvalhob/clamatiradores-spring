package com.clamatiradores.declaracaohab;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clamatiradores.declaracaohab.dto.DeclaracaohabForm;

public interface DeclaracaohabService {

	Page<Declaracaohab> search(String nome, String cpf, Pageable pageable);

	Declaracaohab findById(Integer id);

	Declaracaohab create(DeclaracaohabForm form);

	Declaracaohab update(Integer id, DeclaracaohabForm form);

	void delete(Integer id);

}
