package com.clamatiradores.pagamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PagamentoForm {

	private Integer idPag;

	@NotNull
	private Integer idSocio;

	private String socioNome;
	private String socioCpf;

	@NotBlank(message = "Informe o pagamento")
	private String pagamento;

	public Integer getIdPag() {
		return idPag;
	}

	public void setIdPag(Integer idPag) {
		this.idPag = idPag;
	}

	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public String getSocioNome() {
		return socioNome;
	}

	public void setSocioNome(String socioNome) {
		this.socioNome = socioNome;
	}

	public String getSocioCpf() {
		return socioCpf;
	}

	public void setSocioCpf(String socioCpf) {
		this.socioCpf = socioCpf;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

}
