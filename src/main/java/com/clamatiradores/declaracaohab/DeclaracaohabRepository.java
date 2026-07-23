package com.clamatiradores.declaracaohab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeclaracaohabRepository extends JpaRepository<Declaracaohab, Integer>, JpaSpecificationExecutor<Declaracaohab> {
}
