package com.clamatiradores.habitualidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HabitualidadeRepository extends JpaRepository<Habitualidade, Integer>, JpaSpecificationExecutor<Habitualidade> {
}
