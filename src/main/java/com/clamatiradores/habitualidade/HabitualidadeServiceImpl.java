package com.clamatiradores.habitualidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clamatiradores.habitualidade.dto.HabitualidadeForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Service
@Transactional(readOnly = true)
public class HabitualidadeServiceImpl implements HabitualidadeService {

	private final HabitualidadeRepository repository;
	private final SocioService socioService;

	public HabitualidadeServiceImpl(HabitualidadeRepository repository, SocioService socioService) {
		this.repository = repository;
		this.socioService = socioService;
	}

	@Override
	public Page<Habitualidade> search(String nome, String cpf, Pageable pageable) {
		return repository.findAll(HabitualidadeSpecifications.fromCriteria(nome, cpf), pageable);
	}

	@Override
	public Habitualidade findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new HabitualidadeNotFoundException(id));
	}

	@Override
	@Transactional
	public Habitualidade create(HabitualidadeForm form) {
		Socio socio = socioService.findById(form.getIdSocio());
		Habitualidade entity = new Habitualidade();
		entity.setSocio(socio);
		form.copyTo(entity);
		return repository.save(entity);
	}

	@Override
	@Transactional
	public Habitualidade update(Integer id, HabitualidadeForm form) {
		Habitualidade entity = findById(id);
		form.copyTo(entity);
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if (!repository.existsById(id)) {
			throw new HabitualidadeNotFoundException(id);
		}
		repository.deleteById(id);
	}

}
