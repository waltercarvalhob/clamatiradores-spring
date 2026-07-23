package com.clamatiradores.declaracaomodprova;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clamatiradores.declaracaomodprova.dto.DeclaracaoModProvaForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Controller
@RequestMapping("/declaracoes-modprova")
public class DeclaracaoModProvaController {

	private final DeclaracaoModProvaService service;
	private final SocioService socioService;

	public DeclaracaoModProvaController(DeclaracaoModProvaService service, SocioService socioService) {
		this.service = service;
		this.socioService = socioService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String nome, @RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable, Model model) {
		Page<DeclaracaoModProva> page = service.search(nome, cpf, pageable);
		model.addAttribute("page", page);
		model.addAttribute("nome", nome);
		model.addAttribute("cpf", cpf);
		return "declaracaomodprova/list";
	}

	@GetMapping("/novo")
	public String novo(@RequestParam(required = false) Integer idSocio, Model model) {
		if (idSocio == null) {
			return "redirect:/socio-picker?target=/declaracoes-modprova/novo&targetLabel=Declaracao de Modalidade de Prova";
		}
		Socio socio = socioService.findById(idSocio);
		DeclaracaoModProvaForm form = new DeclaracaoModProvaForm();
		form.setIdSocio(socio.getIdSocio());
		form.setSocioNome(socio.getNome());
		form.setSocioCpf(socio.getCpf());
		model.addAttribute("declaracaoModProvaForm", form);
		return "declaracaomodprova/form";
	}

	@PostMapping
	public String create(@Valid DeclaracaoModProvaForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "declaracaomodprova/form";
		}
		service.create(form);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao de modalidade de prova cadastrada com sucesso.");
		return "redirect:/declaracoes-modprova";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		DeclaracaoModProva entity = service.findById(id);
		model.addAttribute("declaracaoModProvaForm", DeclaracaoModProvaForm.fromEntity(entity));
		return "declaracaomodprova/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid DeclaracaoModProvaForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			form.setIdDecmodprova(id);
			return "declaracaomodprova/form";
		}
		service.update(id, form);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao de modalidade de prova atualizada com sucesso.");
		return "redirect:/declaracoes-modprova";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		service.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao de modalidade de prova excluida com sucesso.");
		return "redirect:/declaracoes-modprova";
	}

}
