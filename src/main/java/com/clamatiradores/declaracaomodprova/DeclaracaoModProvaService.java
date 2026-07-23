package com.clamatiradores.declaracaomodprova;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clamatiradores.declaracaomodprova.dto.DeclaracaoModProvaForm;

public interface DeclaracaoModProvaService {

	Page<DeclaracaoModProva> search(String nome, String cpf, Pageable pageable);

	DeclaracaoModProva findById(Integer id);

	DeclaracaoModProva create(DeclaracaoModProvaForm form);

	DeclaracaoModProva update(Integer id, DeclaracaoModProvaForm form);

	void delete(Integer id);

}
