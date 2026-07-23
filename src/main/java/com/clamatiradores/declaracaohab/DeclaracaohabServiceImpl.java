package com.clamatiradores.declaracaohab;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clamatiradores.declaracaohab.dto.DeclaracaohabForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Service
@Transactional(readOnly = true)
public class DeclaracaohabServiceImpl implements DeclaracaohabService {

	private final DeclaracaohabRepository repository;
	private final SocioService socioService;

	public DeclaracaohabServiceImpl(DeclaracaohabRepository repository, SocioService socioService) {
		this.repository = repository;
		this.socioService = socioService;
	}

	@Override
	public Page<Declaracaohab> search(String nome, String cpf, Pageable pageable) {
		return repository.findAll(DeclaracaohabSpecifications.fromCriteria(nome, cpf), pageable);
	}

	@Override
	public Declaracaohab findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new DeclaracaohabNotFoundException(id));
	}

	@Override
	@Transactional
	public Declaracaohab create(DeclaracaohabForm form) {
		Socio socio = socioService.findById(form.getIdSocio());
		Declaracaohab entity = new Declaracaohab();
		entity.setSocio(socio);
		form.copyTo(entity);
		return repository.save(entity);
	}

	@Override
	@Transactional
	public Declaracaohab update(Integer id, DeclaracaohabForm form) {
		Declaracaohab entity = findById(id);
		form.copyTo(entity);
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if (!repository.existsById(id)) {
			throw new DeclaracaohabNotFoundException(id);
		}
		repository.deleteById(id);
	}

}
