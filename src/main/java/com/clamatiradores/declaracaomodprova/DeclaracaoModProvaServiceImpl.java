package com.clamatiradores.declaracaomodprova;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clamatiradores.declaracaomodprova.dto.DeclaracaoModProvaForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Service
@Transactional(readOnly = true)
public class DeclaracaoModProvaServiceImpl implements DeclaracaoModProvaService {

	private final DeclaracaoModProvaRepository repository;
	private final SocioService socioService;

	public DeclaracaoModProvaServiceImpl(DeclaracaoModProvaRepository repository, SocioService socioService) {
		this.repository = repository;
		this.socioService = socioService;
	}

	@Override
	public Page<DeclaracaoModProva> search(String nome, String cpf, Pageable pageable) {
		return repository.findAll(DeclaracaoModProvaSpecifications.fromCriteria(nome, cpf), pageable);
	}

	@Override
	public DeclaracaoModProva findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new DeclaracaoModProvaNotFoundException(id));
	}

	@Override
	@Transactional
	public DeclaracaoModProva create(DeclaracaoModProvaForm form) {
		Socio socio = socioService.findById(form.getIdSocio());
		DeclaracaoModProva entity = new DeclaracaoModProva();
		entity.setSocio(socio);
		form.copyTo(entity);
		return repository.save(entity);
	}

	@Override
	@Transactional
	public DeclaracaoModProva update(Integer id, DeclaracaoModProvaForm form) {
		DeclaracaoModProva entity = findById(id);
		form.copyTo(entity);
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if (!repository.existsById(id)) {
			throw new DeclaracaoModProvaNotFoundException(id);
		}
		repository.deleteById(id);
	}

}
