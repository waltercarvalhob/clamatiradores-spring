package com.clamatiradores.declaracao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clamatiradores.declaracao.dto.DeclaracaoForm;

public interface DeclaracaoService {

	Page<Declaracao> search(String nome, String cpf, Pageable pageable);

	Declaracao findById(Integer id);

	Declaracao create(DeclaracaoForm form);

	Declaracao update(Integer id, DeclaracaoForm form);

	void delete(Integer id);

}
