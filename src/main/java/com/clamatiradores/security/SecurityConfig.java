package com.clamatiradores.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
			@Value("${app.security.remember-me-key}") String rememberMeKey) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/css/**", "/js/**", "/webjars/**", "/error").permitAll()
				.requestMatchers("/usuarios/**").hasRole("ADMIN")
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/socios", true)
				.permitAll())
			.rememberMe(remember -> remember
				.key(rememberMeKey)
				.rememberMeParameter("remember-me"))
			.logout(logout -> logout
				.logoutSuccessUrl("/login?logout")
				.permitAll())
			// /api/** e consumido por clientes JSON (curl, Postman, um front separado) que
			// nao tem como enviar o token CSRF da sessao do navegador; ainda exige login
			// (sessao autenticada via /login), so nao exige o token de formulario.
			.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

		return http.build();
	}

}
