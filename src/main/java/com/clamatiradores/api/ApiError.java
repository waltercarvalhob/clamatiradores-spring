package com.clamatiradores.api;

import java.util.Map;

/**
 * Corpo de erro padrao das respostas JSON de /api/**. "fieldErrors" so vem
 * preenchido em erros de validacao (400); nos demais casos fica nulo.
 */
public class ApiError {

	private final String error;
	private final Map<String, String> fieldErrors;

	public ApiError(String error) {
		this(error, null);
	}

	public ApiError(String error, Map<String, String> fieldErrors) {
		this.error = error;
		this.fieldErrors = fieldErrors;
	}

	public String getError() {
		return error;
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}

}
