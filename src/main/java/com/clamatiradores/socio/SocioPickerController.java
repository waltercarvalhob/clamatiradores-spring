package com.clamatiradores.socio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.clamatiradores.socio.dto.SocioSearchCriteria;

/**
 * Passo intermediario reutilizado por todos os modulos filiados a um socio
 * (Declaracao, Declaracao de Modalidade de Prova, Declaracao de Habitualidade,
 * Habitualidade, Pagamento): antes de abrir o formulario "novo registro" desses
 * modulos, o usuario precisa escolher a qual socio o registro pertence. Reusa a
 * mesma busca paginada da tela de Socio em vez de duplicar um seletor por modulo.
 */
@Controller
public class SocioPickerController {

	private final SocioService socioService;

	public SocioPickerController(SocioService socioService) {
		this.socioService = socioService;
	}

	@GetMapping("/socio-picker")
	public String picker(@RequestParam String target, @RequestParam(required = false) String targetLabel,
			@ModelAttribute SocioSearchCriteria criteria,
			@PageableDefault(size = 10, sort = "nome") Pageable pageable, Model model) {
		Page<Socio> page = socioService.search(criteria, pageable);
		model.addAttribute("page", page);
		model.addAttribute("criteria", criteria);
		model.addAttribute("target", target);
		model.addAttribute("targetLabel", targetLabel);
		return "socio/picker";
	}

}
