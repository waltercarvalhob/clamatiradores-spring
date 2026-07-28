package com.clamatiradores.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.clamatiradores.common.NotFoundException;

/**
 * Tratamento de erro em JSON para os controllers em com.clamatiradores.api -
 * separado do GlobalExceptionHandler (que devolve paginas HTML para as telas
 * Thymeleaf). basePackages + @Order garantem que este advice, mais especifico,
 * seja escolhido no lugar do generico para requests em /api/**.
 */
@RestControllerAdvice(basePackages = "com.clamatiradores.api")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ApiError handleNotFound(NotFoundException e) {
		return new ApiError(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiError handleValidation(MethodArgumentNotValidException e) {
		Map<String, String> fieldErrors = new LinkedHashMap<>();
		for (FieldError fe : e.getBindingResult().getFieldErrors()) {
			fieldErrors.put(fe.getField(), fe.getDefaultMessage());
		}
		return new ApiError("Dados invalidos", fieldErrors);
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ApiError handleDataIntegrityViolation(DataIntegrityViolationException e) {
		log.warn("Erro de integridade de dados via API", e);
		return new ApiError("Nao foi possivel salvar: verifique dados duplicados ou invalidos (ex.: CPF ja cadastrado).");
	}

}
