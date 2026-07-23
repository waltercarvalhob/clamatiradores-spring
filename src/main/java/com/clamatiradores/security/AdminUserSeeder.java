package com.clamatiradores.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Garante que existe pelo menos um usuario administrador ao subir a aplicacao.
 * O sistema legado nao tinha nenhuma autenticacao funcional (FilterAutenticacao
 * era codigo morto), entao nao ha usuarios/senhas a migrar - este e o primeiro
 * usuario real do sistema.
 */
@Component
public class AdminUserSeeder implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AdminUserSeeder.class);

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final String adminUsername;
	private final String adminPassword;

	public AdminUserSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
			@Value("${app.admin.username}") String adminUsername,
			@Value("${app.admin.password}") String adminPassword) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
	}

	@Override
	public void run(String... args) {
		if (usuarioRepository.findByUsername(adminUsername).isPresent()) {
			return;
		}

		Usuario admin = new Usuario();
		admin.setUsername(adminUsername);
		admin.setPassword(passwordEncoder.encode(adminPassword));
		admin.setEnabled(true);
		admin.setRole("ADMIN");
		usuarioRepository.save(admin);

		if ("changeme".equals(adminPassword)) {
			log.warn("Usuario admin criado com a senha padrao 'changeme'. "
					+ "Defina a variavel de ambiente ADMIN_SEED_PASSWORD antes de expor a aplicacao publicamente.");
		} else {
			log.info("Usuario admin '{}' criado.", adminUsername);
		}
	}

}
