package com.clamatiradores.socio;

import java.time.Year;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clamatiradores.socio.dto.SocioForm;
import com.clamatiradores.socio.dto.SocioSearchCriteria;
import com.clamatiradores.socio.dto.VencimentoItem;

@Controller
@RequestMapping("/socios")
public class SocioController {

	private final SocioService socioService;

	public SocioController(SocioService socioService) {
		this.socioService = socioService;
	}

	@GetMapping
	public String list(@ModelAttribute SocioSearchCriteria criteria,
			@PageableDefault(size = 10, sort = "nome") Pageable pageable, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		Page<Socio> page = socioService.search(criteria, pageable);
		model.addAttribute("page", page);
		model.addAttribute("criteria", criteria);
		return "XMLHttpRequest".equals(requestedWith) ? "socio/list :: resultados" : "socio/list";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("socioForm", new SocioForm());
		return "socio/form";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute("socioForm") SocioForm form, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "socio/form";
		}
		try {
			Socio socio = socioService.create(form);
			redirectAttributes.addFlashAttribute("mensagem", "Socio \"" + socio.getNome() + "\" cadastrado com sucesso.");
			return "redirect:/socios";
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("erro", "Nao foi possivel salvar: verifique se o CPF informado ja nao esta cadastrado para outro socio.");
			return "socio/form";
		}
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		Socio socio = socioService.findById(id);
		model.addAttribute("socioForm", SocioForm.fromEntity(socio));
		return "socio/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid @ModelAttribute("socioForm") SocioForm form,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			form.setIdSocio(id);
			return "socio/form";
		}
		try {
			Socio socio = socioService.update(id, form);
			redirectAttributes.addFlashAttribute("mensagem", "Socio \"" + socio.getNome() + "\" atualizado com sucesso.");
			return "redirect:/socios";
		} catch (DataIntegrityViolationException e) {
			form.setIdSocio(id);
			model.addAttribute("erro", "Nao foi possivel salvar: verifique se o CPF informado ja nao esta cadastrado para outro socio.");
			return "socio/form";
		}
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		socioService.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Socio excluido com sucesso.");
		return "redirect:/socios";
	}

	/**
	 * Substitui as paginas VencimentoPorNome2022/2023/2024/2025/2026.jsp e
	 * VencimentoPorData*.jsp do sistema legado (uma pagina fixa duplicada por ano)
	 * por uma unica tela parametrizada por ano.
	 */
	@GetMapping("/vencimento")
	public String vencimento(@RequestParam(required = false) String ano, @RequestParam(required = false) String nome,
			@PageableDefault(size = 10) Pageable pageable, Model model) {
		int anoAtual = Year.now().getValue();
		String anoEfetivo = StringUtils.hasText(ano) ? ano : String.valueOf(anoAtual);

		List<Integer> anosDisponiveis = List.of(anoAtual - 2, anoAtual - 1, anoAtual, anoAtual + 1);
		Page<VencimentoItem> itens = socioService.vencimentos(anoEfetivo, nome, pageable);

		model.addAttribute("itens", itens);
		model.addAttribute("ano", anoEfetivo);
		model.addAttribute("nome", nome);
		model.addAttribute("anosDisponiveis", anosDisponiveis);
		return "socio/vencimento";
	}

}
