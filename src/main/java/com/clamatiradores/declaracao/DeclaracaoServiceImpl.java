package com.clamatiradores.declaracao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clamatiradores.declaracao.dto.DeclaracaoForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Service
@Transactional(readOnly = true)
public class DeclaracaoServiceImpl implements DeclaracaoService {

	private final DeclaracaoRepository declaracaoRepository;
	private final SocioService socioService;

	public DeclaracaoServiceImpl(DeclaracaoRepository declaracaoRepository, SocioService socioService) {
		this.declaracaoRepository = declaracaoRepository;
		this.socioService = socioService;
	}

	@Override
	public Page<Declaracao> search(String nome, String cpf, Pageable pageable) {
		return declaracaoRepository.findAll(DeclaracaoSpecifications.fromCriteria(nome, cpf), pageable);
	}

	@Override
	public Declaracao findById(Integer id) {
		return declaracaoRepository.findById(id).orElseThrow(() -> new DeclaracaoNotFoundException(id));
	}

	@Override
	@Transactional
	public Declaracao create(DeclaracaoForm form) {
		Socio socio = socioService.findById(form.getIdSocio());
		Declaracao declaracao = new Declaracao();
		declaracao.setSocio(socio);
		form.copyTo(declaracao);
		return declaracaoRepository.save(declaracao);
	}

	@Override
	@Transactional
	public Declaracao update(Integer id, DeclaracaoForm form) {
		Declaracao declaracao = findById(id);
		form.copyTo(declaracao);
		return declaracaoRepository.save(declaracao);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if (!declaracaoRepository.existsById(id)) {
			throw new DeclaracaoNotFoundException(id);
		}
		declaracaoRepository.deleteById(id);
	}

}
