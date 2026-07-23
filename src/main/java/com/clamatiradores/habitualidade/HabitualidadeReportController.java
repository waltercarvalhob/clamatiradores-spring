package com.clamatiradores.habitualidade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clamatiradores.report.ReportService;

@Controller
public class HabitualidadeReportController {

	private final ReportService reportService;

	public HabitualidadeReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Relatorio mensal agregado (equivalente ao legado ServerRelhab) - nao e por
	 * registro individual, e sim um resumo de todos os lancamentos de tbfrequencia
	 * filtrados por mes/ano.
	 */
	@GetMapping("/habitualidades/relatorio")
	@ResponseBody
	public ResponseEntity<byte[]> relatorioMensal(@RequestParam String mes, @RequestParam String ano) {
		Map<String, Object> params = new HashMap<>();
		params.put("mes", mes);
		params.put("ano", ano);
		byte[] pdf = reportService.generatePdf("clamAtiradoesRelHab", params);
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"habitualidade-" + mes + "-" + ano + ".pdf\"")
				.body(pdf);
	}

}
