package com.clamatiradores.declaracaohab;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clamatiradores.report.ReportService;

@Controller
public class DeclaracaohabReportController {

	private final ReportService reportService;
	private final DeclaracaohabService service;

	public DeclaracaohabReportController(ReportService reportService, DeclaracaohabService service) {
		this.reportService = reportService;
		this.service = service;
	}

	@GetMapping("/declaracoes-hab/{id}/relatorio")
	@ResponseBody
	public ResponseEntity<byte[]> permitido(@PathVariable Integer id) {
		return gerar(id, "clamDeclaracaoHab", "declaracao-hab-" + id + ".pdf");
	}

	@GetMapping("/declaracoes-hab/{id}/relatorio-restrito")
	@ResponseBody
	public ResponseEntity<byte[]> restrito(@PathVariable Integer id) {
		return gerar(id, "clamDeclaracaoHabRestrito", "declaracao-hab-restrito-" + id + ".pdf");
	}

	@GetMapping("/declaracoes-hab/{id}/relatorio-completo")
	@ResponseBody
	public ResponseEntity<byte[]> completo(@PathVariable Integer id) {
		Declaracaohab entity = service.findById(id);
		Map<String, Object> params = new HashMap<>();
		params.put("nome", entity.getSocio().getNome());
		params.put("id_dec", entity.getIdDec());
		byte[] pdf = reportService.generatePdf("clamDechabitualidade", params);
		return responder(pdf, "declaracao-hab-completo-" + id + ".pdf");
	}

	private ResponseEntity<byte[]> gerar(Integer id, String reportName, String filename) {
		Declaracaohab entity = service.findById(id);
		Map<String, Object> params = new HashMap<>();
		params.put("nome", entity.getSocio().getNome());
		// Nome de parametro herdado do sistema legado (id_freq), mas recebe o id_dec
		// desta declaracao - o .jrxml original ja usa esse nome, preservado aqui.
		params.put("id_freq", entity.getIdDec());
		byte[] pdf = reportService.generatePdf(reportName, params);
		return responder(pdf, filename);
	}

	private ResponseEntity<byte[]> responder(byte[] pdf, String filename) {
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
				.body(pdf);
	}

}
