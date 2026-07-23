package com.clamatiradores.report;

public class ReportGenerationException extends RuntimeException {

	public ReportGenerationException(String reportName, Throwable cause) {
		super("Falha ao gerar o relatorio '" + reportName + "'", cause);
	}

}
