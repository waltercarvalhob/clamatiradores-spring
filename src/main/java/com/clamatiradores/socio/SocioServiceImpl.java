package com.clamatiradores.socio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clamatiradores.socio.dto.SocioForm;
import com.clamatiradores.socio.dto.SocioSearchCriteria;

@Service
@Transactional(readOnly = true)
public class SocioServiceImpl implements SocioService {

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

}
