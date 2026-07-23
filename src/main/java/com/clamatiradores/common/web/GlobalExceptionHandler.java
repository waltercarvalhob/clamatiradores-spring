package com.clamatiradores.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.clamatiradores.common.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFound() {
		return "error/404";
	}

	/**
	 * Rede de seguranca para erros de banco (ex.: valor maior que o limite de uma coluna,
	 * como "nivel" em tbfrequencia que e varchar(5)) que nao foram tratados localmente
	 * pelo controller do modulo - evita expor stacktrace/JSON cru ao usuario.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public String handleDataIntegrityViolation(DataIntegrityViolationException e) {
		log.warn("Erro de integridade de dados nao tratado localmente pelo controller", e);
		return "error/500";
	}

}
