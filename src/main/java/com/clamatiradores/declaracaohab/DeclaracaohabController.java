package com.clamatiradores.declaracaohab;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clamatiradores.declaracaohab.dto.DeclaracaohabForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Controller
@RequestMapping("/declaracoes-hab")
public class DeclaracaohabController {

	private final DeclaracaohabService service;
	private final SocioService socioService;

	public DeclaracaohabController(DeclaracaohabService service, SocioService socioService) {
		this.service = service;
		this.socioService = socioService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String nome, @RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		Page<Declaracaohab> page = service.search(nome, cpf, pageable);
		model.addAttribute("page", page);
		model.addAttribute("nome", nome);
		model.addAttribute("cpf", cpf);
		return "XMLHttpRequest".equals(requestedWith) ? "declaracaohab/list :: resultados" : "declaracaohab/list";
	}

	@GetMapping("/novo")
	public String novo(@RequestParam(required = false) Integer idSocio, Model model) {
		if (idSocio == null) {
			return "redirect:/socio-picker?target=/declaracoes-hab/novo&targetLabel=Declaracao de Habitualidade";
		}
		Socio socio = socioService.findById(idSocio);
		DeclaracaohabForm form = new DeclaracaohabForm();
		form.setIdSocio(socio.getIdSocio());
		form.setSocioNome(socio.getNome());
		form.setSocioCpf(socio.getCpf());
		model.addAttribute("declaracaohabForm", form);
		return "declaracaohab/form";
	}

	@PostMapping
	public String create(@Valid DeclaracaohabForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "declaracaohab/form";
		}
		service.create(form);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao de habitualidade cadastrada com sucesso.");
		return "redirect:/declaracoes-hab";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		Declaracaohab entity = service.findById(id);
		model.addAttribute("declaracaohabForm", DeclaracaohabForm.fromEntity(entity));
		return "declaracaohab/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid DeclaracaohabForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			form.setIdDec(id);
			return "declaracaohab/form";
		}
		service.update(id, form);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao de habitualidade atualizada com sucesso.");
		return "redirect:/declaracoes-hab";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		service.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao de habitualidade excluida com sucesso.");
		return "redirect:/declaracoes-hab";
	}

}
