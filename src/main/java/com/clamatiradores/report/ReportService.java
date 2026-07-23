package com.clamatiradores.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Reaproveita os .jrxml existentes em WebContent/relatorio do sistema legado
 * (copiados para src/main/resources/relatorio). O SQL de cada relatorio continua
 * embutido no proprio .jrxml (queryString), igual ao comportamento legado nos
 * servlets ServerRel_Ativo/ServerRel_Inativo/ServerRel/RelatorioServer - a unica
 * mudanca e que a Connection agora vem do pool gerenciado pelo Spring (HikariCP)
 * em vez de DriverManager.getConnection() cru.
 */
@Service
public class ReportService {

	private final DataSource dataSource;
	private final Map<String, JasperReport> compiledReports = new ConcurrentHashMap<>();

	public ReportService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public byte[] generatePdf(String reportName, Map<String, Object> params) {
		JasperReport jasperReport = compiledReports.computeIfAbsent(reportName, this::compile);
		try (Connection connection = dataSource.getConnection()) {
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException | SQLException e) {
			throw new ReportGenerationException(reportName, e);
		}
	}

	private JasperReport compile(String reportName) {
		try (InputStream in = new ClassPathResource("relatorio/" + reportName + ".jrxml").getInputStream()) {
			return JasperCompileManager.compileReport(in);
		} catch (Exception e) {
			throw new ReportGenerationException(reportName, e);
		}
	}

}
