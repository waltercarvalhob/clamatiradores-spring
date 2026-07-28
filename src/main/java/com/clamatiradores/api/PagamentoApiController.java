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

import com.clamatiradores.pagamento.Pagamento;
import com.clamatiradores.pagamento.PagamentoService;
import com.clamatiradores.pagamento.dto.PagamentoForm;

/** API JSON equivalente ao PagamentoController (Thymeleaf), mesma service por baixo. */
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoApiController {

	private final PagamentoService pagamentoService;

	public PagamentoApiController(PagamentoService pagamentoService) {
		this.pagamentoService = pagamentoService;
	}

	@GetMapping
	public Page<Pagamento> search(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable) {
		return pagamentoService.search(nome, cpf, pageable);
	}

	@GetMapping("/{id}")
	public Pagamento findById(@PathVariable Integer id) {
		return pagamentoService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Pagamento> create(@Valid @RequestBody PagamentoForm form) {
		Pagamento pagamento = pagamentoService.create(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
	}

	@PutMapping("/{id}")
	public Pagamento update(@PathVariable Integer id, @Valid @RequestBody PagamentoForm form) {
		return pagamentoService.update(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		pagamentoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
