package com.clamatiradores.pagamento;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
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

import com.clamatiradores.pagamento.dto.PagamentoForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

	private final PagamentoService pagamentoService;
	private final SocioService socioService;

	public PagamentoController(PagamentoService pagamentoService, SocioService socioService) {
		this.pagamentoService = pagamentoService;
		this.socioService = socioService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String nome, @RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable, Model model) {
		Page<Pagamento> page = pagamentoService.search(nome, cpf, pageable);
		model.addAttribute("page", page);
		model.addAttribute("nome", nome);
		model.addAttribute("cpf", cpf);
		return "pagamento/list";
	}

	@GetMapping("/novo")
	public String novo(@RequestParam(required = false) Integer idSocio, Model model) {
		if (idSocio == null) {
			return "redirect:/socio-picker?target=/pagamentos/novo&targetLabel=Pagamento";
		}
		Socio socio = socioService.findById(idSocio);
		PagamentoForm form = new PagamentoForm();
		form.setIdSocio(socio.getIdSocio());
		form.setSocioNome(socio.getNome());
		form.setSocioCpf(socio.getCpf());
		model.addAttribute("pagamentoForm", form);
		return "pagamento/form";
	}

	@PostMapping
	public String create(@Valid PagamentoForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "pagamento/form";
		}
		try {
			pagamentoService.create(form);
			redirectAttributes.addFlashAttribute("mensagem", "Pagamento cadastrado com sucesso.");
			return "redirect:/pagamentos";
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("erro", "Nao foi possivel salvar o pagamento.");
			return "pagamento/form";
		}
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		Pagamento pagamento = pagamentoService.findById(id);
		PagamentoForm form = new PagamentoForm();
		form.setIdPag(pagamento.getIdPag());
		form.setIdSocio(pagamento.getSocio().getIdSocio());
		form.setSocioNome(pagamento.getSocio().getNome());
		form.setSocioCpf(pagamento.getSocio().getCpf());
		form.setPagamento(pagamento.getPagamento());
		model.addAttribute("pagamentoForm", form);
		return "pagamento/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid PagamentoForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			form.setIdPag(id);
			return "pagamento/form";
		}
		try {
			pagamentoService.update(id, form);
			redirectAttributes.addFlashAttribute("mensagem", "Pagamento atualizado com sucesso.");
			return "redirect:/pagamentos";
		} catch (DataIntegrityViolationException e) {
			form.setIdPag(id);
			model.addAttribute("erro", "Nao foi possivel salvar o pagamento.");
			return "pagamento/form";
		}
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		pagamentoService.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Pagamento excluido com sucesso.");
		return "redirect:/pagamentos";
	}

}
