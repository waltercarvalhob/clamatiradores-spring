package com.clamatiradores.report;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/socios/relatorios")
public class SocioReportController {

	private final ReportService reportService;

	public SocioReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Filtro proprio dos relatorios de Ativo/Inativo (dia/mes/ano de validade, ou uma
	 * data abreviada tipo "15/08"/"08/2026") - independente do filtro da tela de
	 * Vencimento de Socios. Todos opcionais: em branco imprime a lista completa.
	 */
	@GetMapping("/ativo")
	@ResponseBody
	public ResponseEntity<byte[]> ativo(@RequestParam(required = false) String dia,
			@RequestParam(required = false) String mes, @RequestParam(required = false) String ano,
			@RequestParam(required = false) String dataAbreviada) {
		return pdf("socioAtivo", "socios-ativos.pdf", dia, mes, ano, dataAbreviada);
	}

	@GetMapping("/inativo")
	@ResponseBody
	public ResponseEntity<byte[]> inativo(@RequestParam(required = false) String dia,
			@RequestParam(required = false) String mes, @RequestParam(required = false) String ano,
			@RequestParam(required = false) String dataAbreviada) {
		return pdf("socioInativo", "socios-inativos.pdf", dia, mes, ano, dataAbreviada);
	}

	@GetMapping("/clam")
	@ResponseBody
	public ResponseEntity<byte[]> clam(@RequestParam(required = false) String mes,
			@RequestParam(required = false) String ano) {
		Map<String, Object> params = new HashMap<>();
		params.put("mes", mes);
		params.put("ano", ano);
		byte[] pdf = reportService.generatePdf("clamAtiradoesRel", params);
		return respond(pdf, "relatorio-geral.pdf");
	}

	private ResponseEntity<byte[]> pdf(String reportName, String filename, String dia, String mes, String ano,
			String dataAbreviada) {
		// JasperFillManager preenche parametros internos (ex.: REPORT_CONNECTION) no mapa
		// recebido, entao precisa ser mutavel - Map.of() lanca UnsupportedOperationException.
		// Filtro opcional via LIKE + '%' (nao "$P{x} IS NULL OR ...") pra seguir o mesmo
		// padrao ja usado e comprovado em clamAtiradoesRel.jrxml.
		Map<String, Object> params = new HashMap<>();
		params.put("dia", StringUtils.hasText(dia) ? doisDigitos(dia) : "%");
		params.put("mes", StringUtils.hasText(mes) ? doisDigitos(mes) : "%");
		params.put("ano", StringUtils.hasText(ano) ? ano.trim() : "%");
		params.put("dataAbreviada", StringUtils.hasText(dataAbreviada) ? "%" + dataAbreviada.trim() + "%" : "%");
		byte[] pdf = reportService.generatePdf(reportName, params);
		return respond(pdf, filename);
	}

	/**
	 * A validade e armazenada como texto "DD/MM/YYYY" sempre com dois digitos -
	 * "5" digitado no filtro de dia/mes precisa virar "05" pra bater com o
	 * SUBSTRING usado na consulta do relatorio.
	 */
	private String doisDigitos(String valor) {
		if (!StringUtils.hasText(valor)) {
			return null;
		}
		String limpo = valor.trim();
		return limpo.length() == 1 ? "0" + limpo : limpo;
	}

	private ResponseEntity<byte[]> respond(byte[] pdf, String filename) {
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
				.body(pdf);
	}

}
