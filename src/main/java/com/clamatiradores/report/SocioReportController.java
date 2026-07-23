package com.clamatiradores.report;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	 * Nota: socioAtivo.jrxml/socioInativo.jrxml nao declaram parametros (o filtro
	 * ativo/inativo ja esta embutido no SQL do proprio relatorio), entao nenhum
	 * parametro precisa ser passado aqui - assim como no servlet legado ServerRel_Ativo,
	 * que passava nome/cpf/etc. mas o relatorio os ignorava.
	 */
	@GetMapping("/ativo")
	@ResponseBody
	public ResponseEntity<byte[]> ativo() {
		return pdf("socioAtivo", "socios-ativos.pdf");
	}

	@GetMapping("/inativo")
	@ResponseBody
	public ResponseEntity<byte[]> inativo() {
		return pdf("socioInativo", "socios-inativos.pdf");
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

	private ResponseEntity<byte[]> pdf(String reportName, String filename) {
		// JasperFillManager preenche parametros internos (ex.: REPORT_CONNECTION) no mapa
		// recebido, entao precisa ser mutavel - Map.of() lanca UnsupportedOperationException.
		byte[] pdf = reportService.generatePdf(reportName, new HashMap<>());
		return respond(pdf, filename);
	}

	private ResponseEntity<byte[]> respond(byte[] pdf, String filename) {
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
				.body(pdf);
	}

}
