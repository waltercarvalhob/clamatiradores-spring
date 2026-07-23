package com.clamatiradores.declaracaomodprova;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeclaracaoModProvaRepository
		extends JpaRepository<DeclaracaoModProva, Integer>, JpaSpecificationExecutor<DeclaracaoModProva> {
}
