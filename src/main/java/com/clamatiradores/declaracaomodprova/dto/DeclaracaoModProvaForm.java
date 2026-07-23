package com.clamatiradores.declaracaomodprova.dto;

import jakarta.validation.constraints.NotNull;

import com.clamatiradores.declaracaomodprova.DeclaracaoModProva;

public class DeclaracaoModProvaForm {

	private Integer idDecmodprova;

	@NotNull
	private Integer idSocio;

	private String socioNome;
	private String socioCpf;

	private String dia;
	private String mes;
	private String ano;
	private String dataatual;
	private String dataemissao;
	private String datavalidade;

	public static DeclaracaoModProvaForm fromEntity(DeclaracaoModProva d) {
		DeclaracaoModProvaForm form = new DeclaracaoModProvaForm();
		form.setIdDecmodprova(d.getIdDecmodprova());
		form.setIdSocio(d.getSocio().getIdSocio());
		form.setSocioNome(d.getSocio().getNome());
		form.setSocioCpf(d.getSocio().getCpf());
		form.setDia(d.getDia());
		form.setMes(d.getMes());
		form.setAno(d.getAno());
		form.setDataatual(d.getDataatual());
		form.setDataemissao(d.getDataemissao());
		form.setDatavalidade(d.getDatavalidade());
		return form;
	}

	public void copyTo(DeclaracaoModProva d) {
		d.setDia(dia);
		d.setMes(mes);
		d.setAno(ano);
		d.setDataatual(dataatual);
		d.setDataemissao(dataemissao);
		d.setDatavalidade(datavalidade);
	}

	public Integer getIdDecmodprova() {
		return idDecmodprova;
	}

	public void setIdDecmodprova(Integer idDecmodprova) {
		this.idDecmodprova = idDecmodprova;
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

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getDataatual() {
		return dataatual;
	}

	public void setDataatual(String dataatual) {
		this.dataatual = dataatual;
	}

	public String getDataemissao() {
		return dataemissao;
	}

	public void setDataemissao(String dataemissao) {
		this.dataemissao = dataemissao;
	}

	public String getDatavalidade() {
		return datavalidade;
	}

	public void setDatavalidade(String datavalidade) {
		this.datavalidade = datavalidade;
	}

}
