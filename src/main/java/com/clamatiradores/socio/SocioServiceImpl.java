package com.clamatiradores.socio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.clamatiradores.socio.dto.SocioForm;
import com.clamatiradores.socio.dto.SocioSearchCriteria;
import com.clamatiradores.socio.dto.VencimentoItem;

@Service
@Transactional(readOnly = true)
public class SocioServiceImpl implements SocioService {

	private static final DateTimeFormatter DATA_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final SocioRepository socioRepository;

	public SocioServiceImpl(SocioRepository socioRepository) {
		this.socioRepository = socioRepository;
	}

	@Override
	public Page<Socio> search(SocioSearchCriteria criteria, Pageable pageable) {
		return socioRepository.findAll(SocioSpecifications.fromCriteria(criteria), pageable);
	}

	@Override
	public Socio findById(Integer id) {
		return socioRepository.findById(id).orElseThrow(() -> new SocioNotFoundException(id));
	}

	@Override
	@Transactional
	public Socio create(SocioForm form) {
		Socio socio = new Socio();
		form.copyTo(socio);
		return socioRepository.save(socio);
	}

	@Override
	@Transactional
	public Socio update(Integer id, SocioForm form) {
		Socio socio = findById(id);
		form.copyTo(socio);
		return socioRepository.save(socio);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if (!socioRepository.existsById(id)) {
			throw new SocioNotFoundException(id);
		}
		socioRepository.deleteById(id);
	}

	@Override
	public List<VencimentoItem> vencimentos(String ano, String nome) {
		String nomeFiltro = StringUtils.hasText(nome) ? nome.trim() : null;
		LocalDate hoje = LocalDate.now();
		return socioRepository.findVencimentos(ano, nomeFiltro).stream()
				.map(socio -> new VencimentoItem(socio, isVencido(socio, hoje)))
				.collect(Collectors.toList());
	}

	private boolean isVencido(Socio socio, LocalDate hoje) {
		try {
			return LocalDate.parse(socio.getValidade(), DATA_BR).isBefore(hoje);
		} catch (Exception e) {
			return false;
		}
	}

}
