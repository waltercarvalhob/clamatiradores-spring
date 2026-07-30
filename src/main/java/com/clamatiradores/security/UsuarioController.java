package com.clamatiradores.security;

import java.security.Principal;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clamatiradores.security.dto.UsuarioForm;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public String list(@RequestParam(required = false) String username,
			@PageableDefault(size = 10, sort = "username") Pageable pageable, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		Page<Usuario> page = usuarioService.search(username, pageable);
		model.addAttribute("page", page);
		model.addAttribute("username", username);
		return "XMLHttpRequest".equals(requestedWith) ? "usuario/list :: resultados" : "usuario/list";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("usuarioForm", new UsuarioForm());
		return "usuario/form";
	}

	@PostMapping
	public String create(@Valid UsuarioForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (!StringUtils.hasText(form.getPassword())) {
			result.rejectValue("password", "required", "Informe uma senha");
		} else if (form.getPassword().length() < 6) {
			result.rejectValue("password", "size", "A senha deve ter pelo menos 6 caracteres");
		}
		if (result.hasErrors()) {
			return "usuario/form";
		}
		try {
			Usuario usuario = usuarioService.create(form);
			redirectAttributes.addFlashAttribute("mensagem",
					"Usuario \"" + usuario.getUsername() + "\" cadastrado com sucesso.");
			return "redirect:/usuarios";
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("erro", "Nao foi possivel salvar: verifique se o nome de usuario ja nao esta em uso.");
			return "usuario/form";
		}
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Integer id, Model model) {
		Usuario usuario = usuarioService.findById(id);
		UsuarioForm form = new UsuarioForm();
		form.setIdUsuario(usuario.getIdUsuario());
		form.setUsername(usuario.getUsername());
		form.setRole(usuario.getRole());
		form.setEnabled(usuario.isEnabled());
		model.addAttribute("usuarioForm", form);
		return "usuario/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Integer id, @Valid UsuarioForm form, BindingResult result, Model model,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (StringUtils.hasText(form.getPassword()) && form.getPassword().length() < 6) {
			result.rejectValue("password", "size", "A senha deve ter pelo menos 6 caracteres");
		}
		if (result.hasErrors()) {
			form.setIdUsuario(id);
			return "usuario/form";
		}
		try {
			Usuario usuario = usuarioService.update(id, form, principal.getName());
			redirectAttributes.addFlashAttribute("mensagem",
					"Usuario \"" + usuario.getUsername() + "\" atualizado com sucesso.");
			return "redirect:/usuarios";
		} catch (DataIntegrityViolationException e) {
			form.setIdUsuario(id);
			model.addAttribute("erro", "Nao foi possivel salvar: verifique se o nome de usuario ja nao esta em uso.");
			return "usuario/form";
		} catch (UsuarioOperationException e) {
			form.setIdUsuario(id);
			model.addAttribute("erro", e.getMessage());
			return "usuario/form";
		}
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes, Principal principal) {
		try {
			usuarioService.delete(id, principal.getName());
			redirectAttributes.addFlashAttribute("mensagem", "Usuario excluido com sucesso.");
		} catch (UsuarioOperationException e) {
			redirectAttributes.addFlashAttribute("erro", e.getMessage());
		}
		return "redirect:/usuarios";
	}

}
