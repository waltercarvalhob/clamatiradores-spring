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

import com.clamatiradores.habitualidade.Habitualidade;
import com.clamatiradores.habitualidade.HabitualidadeService;
import com.clamatiradores.habitualidade.dto.HabitualidadeForm;

/** API JSON equivalente ao HabitualidadeController (Thymeleaf), mesma service por baixo. */
@RestController
@RequestMapping("/api/habitualidades")
public class HabitualidadeApiController {

	private final HabitualidadeService habitualidadeService;

	public HabitualidadeApiController(HabitualidadeService habitualidadeService) {
		this.habitualidadeService = habitualidadeService;
	}

	@GetMapping
	public Page<Habitualidade> search(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable) {
		return habitualidadeService.search(nome, cpf, pageable);
	}

	@GetMapping("/{id}")
	public Habitualidade findById(@PathVariable Integer id) {
		return habitualidadeService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Habitualidade> create(@Valid @RequestBody HabitualidadeForm form) {
		Habitualidade habitualidade = habitualidadeService.create(form);
		return ResponseEntity.status(HttpStatus.CREATED).body(habitualidade);
	}

	@PutMapping("/{id}")
	public Habitualidade update(@PathVariable Integer id, @Valid @RequestBody HabitualidadeForm form) {
		return habitualidadeService.update(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		habitualidadeService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
