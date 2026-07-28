package com.clamatiradores.api;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;
import com.clamatiradores.socio.dto.SocioForm;
import com.clamatiradores.socio.dto.SocioSearchCriteria;

/**
 * API JSON equivalente ao SocioController (Thymeleaf) - mesma SocioService por
 * baixo, entao qualquer regra de negocio (ex.: CPF unico) vale para os dois.
 */
@RestController
@RequestMapping("/api/socios")
public class SocioApiController {

	private final SocioService socioService;

	public SocioApiController(SocioService socioService) {
		this.socioService = socioService;
	}

	@GetMapping
	public Page<Socio> search(@ModelAttribute SocioSearchCriteria criteria,
			@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
		return socioService.search(criteria, pageable);
	}

	@GetMapping("/{id}")
	public Socio findById(@PathVariable Integer id) {
		return socioService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Socio> create(@Valid @RequestBody SocioForm form) {
		Socio socio = socioService.create(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(socio);
	}

	@PutMapping("/{id}")
	public Socio update(@PathVariable Integer id, @Valid @RequestBody SocioForm form) {
		return socioService.update(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		socioService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
