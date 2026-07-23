package com.clamatiradores.socio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clamatiradores.socio.dto.SocioForm;
import com.clamatiradores.socio.dto.SocioSearchCriteria;
import com.clamatiradores.socio.dto.VencimentoItem;

public interface SocioService {

	Page<Socio> search(SocioSearchCriteria criteria, Pageable pageable);

	Socio findById(Integer id);

	Socio create(SocioForm form);

	Socio update(Integer id, SocioForm form);

	void delete(Integer id);

	List<VencimentoItem> vencimentos(String ano, String nome);

}
