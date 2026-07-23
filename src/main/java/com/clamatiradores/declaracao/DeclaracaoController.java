package com.clamatiradores.declaracao;

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

import com.clamatiradores.declaracao.dto.DeclaracaoForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Controller
@RequestMapping("/declaracoes")
public class DeclaracaoController {

	private final DeclaracaoService declaracaoService;
	private final SocioService socioService;

	public DeclaracaoController(DeclaracaoService declaracaoService, SocioService socioService) {
		this.declaracaoService = declaracaoService;
		this.socioService = socioService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String nome, @RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable, Model model) {
		Page<Declaracao> page = declaracaoService.search(nome, cpf, pageable);
		model.addAttribute("page", page);
		model.addAttribute("nome", nome);
		model.addAttribute("cpf", cpf);
		return "declaracao/list";
	}

	@GetMapping("/novo")
	public String novo(@RequestParam(required = false) Integer idSocio, Model model) {
		if (idSocio == null) {
			return "redirect:/socio-picker?target=/declaracoes/novo&targetLabel=Declaracao";
		}
		Socio socio = socioService.findById(idSocio);
		DeclaracaoForm form = new DeclaracaoForm();
		form.setIdSocio(socio.getIdSocio());
		form.setSocioNome(socio.getNome());
		form.setSocioCpf(socio.getCpf());
		form.setEndereco(socio.getEndereco());
		form.setCr(socio.getNumcr());
		model.addAttribute("declaracaoForm", form);
		return "declaracao/form";
	}

	@PostMapping
	public String create(@Valid DeclaracaoForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "declaracao/form";
		}
		declaracaoService.create(form);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao cadastrada com sucesso.");
		return "redirect:/declaracoes";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		Declaracao declaracao = declaracaoService.findById(id);
		model.addAttribute("declaracaoForm", DeclaracaoForm.fromEntity(declaracao));
		return "declaracao/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid DeclaracaoForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			form.setIdDec(id);
			return "declaracao/form";
		}
		declaracaoService.update(id, form);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao atualizada com sucesso.");
		return "redirect:/declaracoes";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		declaracaoService.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Declaracao excluida com sucesso.");
		return "redirect:/declaracoes";
	}

}
