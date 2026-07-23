package com.clamatiradores.declaracao;

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
public class DeclaracaoReportController {

	private final ReportService reportService;
	private final DeclaracaoService declaracaoService;

	public DeclaracaoReportController(ReportService reportService, DeclaracaoService declaracaoService) {
		this.reportService = reportService;
		this.declaracaoService = declaracaoService;
	}

	@GetMapping("/declaracoes/{id}/relatorio")
	@ResponseBody
	public ResponseEntity<byte[]> relatorio(@PathVariable Integer id) {
		Declaracao declaracao = declaracaoService.findById(id);
		Map<String, Object> params = new HashMap<>();
		params.put("nome", declaracao.getSocio().getNome());
		params.put("id_dec", declaracao.getIdDec());
		byte[] pdf = reportService.generatePdf("clamdecfiliacao", params);
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"declaracao-" + id + ".pdf\"")
				.body(pdf);
	}

}
