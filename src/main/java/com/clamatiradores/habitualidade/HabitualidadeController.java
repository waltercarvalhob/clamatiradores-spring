package com.clamatiradores.habitualidade;

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

import com.clamatiradores.habitualidade.dto.HabitualidadeForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Controller
@RequestMapping("/habitualidades")
public class HabitualidadeController {

	private final HabitualidadeService service;
	private final SocioService socioService;

	public HabitualidadeController(HabitualidadeService service, SocioService socioService) {
		this.service = service;
		this.socioService = socioService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String nome, @RequestParam(required = false) String cpf,
			@PageableDefault(size = 10) Pageable pageable, Model model) {
		Page<Habitualidade> page = service.search(nome, cpf, pageable);
		model.addAttribute("page", page);
		model.addAttribute("nome", nome);
		model.addAttribute("cpf", cpf);
		return "habitualidade/list";
	}

	@GetMapping("/novo")
	public String novo(@RequestParam(required = false) Integer idSocio, Model model) {
		if (idSocio == null) {
			return "redirect:/socio-picker?target=/habitualidades/novo&targetLabel=Habitualidade";
		}
		Socio socio = socioService.findById(idSocio);
		HabitualidadeForm form = new HabitualidadeForm();
		form.setIdSocio(socio.getIdSocio());
		form.setSocioNome(socio.getNome());
		form.setSocioCpf(socio.getCpf());
		form.setCpf(socio.getCpf());
		form.setEndereco(socio.getEndereco());
		form.setNumcr(socio.getNumcr());
		form.setDatafiliacao(socio.getFiliacao());
		form.setDatavalidade(socio.getValidade());
		model.addAttribute("habitualidadeForm", form);
		return "habitualidade/form";
	}

	@PostMapping
	public String create(@Valid HabitualidadeForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "habitualidade/form";
		}
		service.create(form);
		redirectAttributes.addFlashAttribute("mensagem", "Habitualidade cadastrada com sucesso.");
		return "redirect:/habitualidades";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		Habitualidade entity = service.findById(id);
		model.addAttribute("habitualidadeForm", HabitualidadeForm.fromEntity(entity));
		return "habitualidade/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid HabitualidadeForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			form.setIdFreq(id);
			return "habitualidade/form";
		}
		service.update(id, form);
		redirectAttributes.addFlashAttribute("mensagem", "Habitualidade atualizada com sucesso.");
		return "redirect:/habitualidades";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		service.delete(id);
		redirectAttributes.addFlashAttribute("mensagem", "Habitualidade excluida com sucesso.");
		return "redirect:/habitualidades";
	}

}
