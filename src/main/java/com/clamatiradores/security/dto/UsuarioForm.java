package com.clamatiradores.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioForm {

	private Integer idUsuario;

	@NotBlank(message = "Informe o nome de usuario")
	@Size(max = 50, message = "Nome de usuario muito longo")
	private String username;

	/**
	 * Sem @Size aqui de proposito: em branco significa "manter a senha atual"
	 * na edicao, e obrigatoria no cadastro - as duas regras sao verificadas
	 * manualmente no UsuarioController, onde o contexto (criar x editar) e
	 * conhecido.
	 */
	private String password;

	@NotBlank(message = "Selecione o perfil")
	private String role = "USER";

	private boolean enabled = true;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
