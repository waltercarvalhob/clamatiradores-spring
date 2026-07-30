package com.clamatiradores.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.clamatiradores.security.dto.UsuarioForm;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

	private static final String ROLE_ADMIN = "ADMIN";

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Page<Usuario> search(String username, Pageable pageable) {
		return usuarioRepository.findAll(UsuarioSpecifications.fromCriteria(username), pageable);
	}

	@Override
	public Usuario findById(Integer id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
	}

	@Override
	@Transactional
	public Usuario create(UsuarioForm form) {
		Usuario usuario = new Usuario();
		usuario.setUsername(form.getUsername().trim());
		usuario.setPassword(passwordEncoder.encode(form.getPassword()));
		usuario.setRole(form.getRole());
		usuario.setEnabled(form.isEnabled());
		return usuarioRepository.save(usuario);
	}

	@Override
	@Transactional
	public Usuario update(Integer id, UsuarioForm form, String currentUsername) {
		Usuario usuario = findById(id);

		boolean eraAdminAtivo = usuario.isEnabled() && ROLE_ADMIN.equals(usuario.getRole());
		boolean continuaraAdminAtivo = form.isEnabled() && ROLE_ADMIN.equals(form.getRole());
		if (eraAdminAtivo && !continuaraAdminAtivo && isUltimoAdminAtivo(usuario)) {
			throw new UsuarioOperationException(
					"Nao e possivel remover o ultimo administrador ativo do sistema.");
		}

		usuario.setUsername(form.getUsername().trim());
		if (StringUtils.hasText(form.getPassword())) {
			usuario.setPassword(passwordEncoder.encode(form.getPassword()));
		}
		usuario.setRole(form.getRole());
		usuario.setEnabled(form.isEnabled());
		return usuarioRepository.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Integer id, String currentUsername) {
		Usuario usuario = findById(id);
		if (usuario.getUsername().equals(currentUsername)) {
			throw new UsuarioOperationException("Voce nao pode excluir o proprio usuario.");
		}
		if (usuario.isEnabled() && ROLE_ADMIN.equals(usuario.getRole()) && isUltimoAdminAtivo(usuario)) {
			throw new UsuarioOperationException(
					"Nao e possivel excluir o ultimo administrador ativo do sistema.");
		}
		usuarioRepository.delete(usuario);
	}

	private boolean isUltimoAdminAtivo(Usuario usuario) {
		return usuarioRepository.countByRoleAndEnabledTrueAndIdUsuarioNot(ROLE_ADMIN, usuario.getIdUsuario()) == 0;
	}

}
