package com.clamatiradores.declaracao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeclaracaoRepository extends JpaRepository<Declaracao, Integer>, JpaSpecificationExecutor<Declaracao> {
}
