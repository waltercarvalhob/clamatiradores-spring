package com.clamatiradores.api;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clamatiradores.declaracaohab.Declaracaohab;
import com.clamatiradores.declaracaohab.DeclaracaohabService;
import com.clamatiradores.declaracaohab.dto.DeclaracaohabForm;

/** API JSON equivalente ao DeclaracaohabController (Thymeleaf), mesma service por baixo. */
@RestController
@RequestMapping("/api/declaracoes-hab")
public class DeclaracaohabApiController {

	private final DeclaracaohabService declaracaohabService;

	public DeclaracaohabApiController(DeclaracaohabService declaracaohabService) {
		this.declaracaohabService = declaracaohabService;
	}

	@GetMapping
	public Page<Declaracaohab> search(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable) {
		return declaracaohabService.search(nome, cpf, pageable);
	}

	@GetMapping("/{id}")
	public Declaracaohab findById(@PathVariable Integer id) {
		return declaracaohabService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Declaracaohab> create(@Valid @RequestBody DeclaracaohabForm form) {
		Declaracaohab declaracaohab = declaracaohabService.create(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(declaracaohab);
	}

	@PutMapping("/{id}")
	public Declaracaohab update(@PathVariable Integer id, @Valid @RequestBody DeclaracaohabForm form) {
		return declaracaohabService.update(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		declaracaohabService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
