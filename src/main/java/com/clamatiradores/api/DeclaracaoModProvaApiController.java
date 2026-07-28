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

import com.clamatiradores.declaracaomodprova.DeclaracaoModProva;
import com.clamatiradores.declaracaomodprova.DeclaracaoModProvaService;
import com.clamatiradores.declaracaomodprova.dto.DeclaracaoModProvaForm;

/** API JSON equivalente ao DeclaracaoModProvaController (Thymeleaf), mesma service por baixo. */
@RestController
@RequestMapping("/api/declaracoes-modprova")
public class DeclaracaoModProvaApiController {

	private final DeclaracaoModProvaService declaracaoModProvaService;

	public DeclaracaoModProvaApiController(DeclaracaoModProvaService declaracaoModProvaService) {
		this.declaracaoModProvaService = declaracaoModProvaService;
	}

	@GetMapping
	public Page<DeclaracaoModProva> search(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable) {
		return declaracaoModProvaService.search(nome, cpf, pageable);
	}

	@GetMapping("/{id}")
	public DeclaracaoModProva findById(@PathVariable Integer id) {
		return declaracaoModProvaService.findById(id);
	}

	@PostMapping
	public ResponseEntity<DeclaracaoModProva> create(@Valid @RequestBody DeclaracaoModProvaForm form) {
		DeclaracaoModProva declaracaoModProva = declaracaoModProvaService.create(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(declaracaoModProva);
	}

	@PutMapping("/{id}")
	public DeclaracaoModProva update(@PathVariable Integer id, @Valid @RequestBody DeclaracaoModProvaForm form) {
		return declaracaoModProvaService.update(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		declaracaoModProvaService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
