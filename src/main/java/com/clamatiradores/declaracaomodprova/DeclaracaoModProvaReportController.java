package com.clamatiradores.declaracaomodprova;

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
public class DeclaracaoModProvaReportController {

	private final ReportService reportService;
	private final DeclaracaoModProvaService service;

	public DeclaracaoModProvaReportController(ReportService reportService, DeclaracaoModProvaService service) {
		this.reportService = reportService;
		this.service = service;
	}

	@GetMapping("/declaracoes-modprova/{id}/relatorio")
	@ResponseBody
	public ResponseEntity<byte[]> relatorio(@PathVariable Integer id) {
		DeclaracaoModProva entity = service.findById(id);
		Map<String, Object> params = new HashMap<>();
		params.put("nome", entity.getSocio().getNome());
		params.put("id_decmodprova", entity.getIdDecmodprova());
		byte[] pdf = reportService.generatePdf("decModProva", params);
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"declaracao-modprova-" + id + ".pdf\"")
				.body(pdf);
	}

}
