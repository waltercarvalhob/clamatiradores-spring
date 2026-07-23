package com.clamatiradores.socio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SocioRepository extends JpaRepository<Socio, Integer>, JpaSpecificationExecutor<Socio> {

	boolean existsByCpf(String cpf);

}
