package com.clamatiradores.security;

/**
 * Violacao de uma regra de negocio do cadastro de usuarios (ex.: excluir a
 * propria conta, ou remover o ultimo administrador ativo) - nao e um erro de
 * validacao de formulario nem de integridade do banco, por isso tem sua
 * propria excecao, tratada localmente pelo UsuarioController.
 */
public class UsuarioOperationException extends RuntimeException {

	public UsuarioOperationException(String message) {
		super(message);
	}

}
