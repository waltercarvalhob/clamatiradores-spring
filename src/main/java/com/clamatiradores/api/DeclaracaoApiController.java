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

import com.clamatiradores.declaracao.Declaracao;
import com.clamatiradores.declaracao.DeclaracaoService;
import com.clamatiradores.declaracao.dto.DeclaracaoForm;

/** API JSON equivalente ao DeclaracaoController (Thymeleaf), mesma DeclaracaoService por baixo. */
@RestController
@RequestMapping("/api/declaracoes")
public class DeclaracaoApiController {

	private final DeclaracaoService declaracaoService;

	public DeclaracaoApiController(DeclaracaoService declaracaoService) {
		this.declaracaoService = declaracaoService;
	}

	@GetMapping
	public Page<Declaracao> search(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable) {
		return declaracaoService.search(nome, cpf, pageable);
	}

	@GetMapping("/{id}")
	public Declaracao findById(@PathVariable Integer id) {
		return declaracaoService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Declaracao> create(@Valid @RequestBody DeclaracaoForm form) {
		Declaracao declaracao = declaracaoService.create(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(declaracao);
	}

	@PutMapping("/{id}")
	public Declaracao update(@PathVariable Integer id, @Valid @RequestBody DeclaracaoForm form) {
		return declaracaoService.update(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		declaracaoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
