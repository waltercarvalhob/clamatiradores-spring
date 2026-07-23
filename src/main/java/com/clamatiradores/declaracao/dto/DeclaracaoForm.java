package com.clamatiradores.declaracao.dto;

import jakarta.validation.constraints.NotNull;

import com.clamatiradores.declaracao.Declaracao;

public class DeclaracaoForm {

	private Integer idDec;

	@NotNull
	private Integer idSocio;

	private String socioNome;
	private String socioCpf;

	private String datainsc;
	private String dia;
	private String mes;
	private String ano;
	private String dataemissao;
	private String datavalidade;
	private String endereco;
	private String cr;

	public static DeclaracaoForm fromEntity(Declaracao d) {
		DeclaracaoForm form = new DeclaracaoForm();
		form.setIdDec(d.getIdDec());
		form.setIdSocio(d.getSocio().getIdSocio());
		form.setSocioNome(d.getSocio().getNome());
		form.setSocioCpf(d.getSocio().getCpf());
		form.setDatainsc(d.getDatainsc());
		form.setDia(d.getDia());
		form.setMes(d.getMes());
		form.setAno(d.getAno());
		form.setDataemissao(d.getDataemissao());
		form.setDatavalidade(d.getDatavalidade());
		form.setEndereco(d.getEndereco());
		form.setCr(d.getCr());
		return form;
	}

	public void copyTo(Declaracao d) {
		d.setDatainsc(datainsc);
		d.setDia(dia);
		d.setMes(mes);
		d.setAno(ano);
		d.setDataemissao(dataemissao);
		d.setDatavalidade(datavalidade);
		d.setEndereco(endereco);
		d.setCr(cr);
	}

	public Integer getIdDec() {
		return idDec;
	}

	public void setIdDec(Integer idDec) {
		this.idDec = idDec;
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

	public String getDatainsc() {
		return datainsc;
	}

	public void setDatainsc(String datainsc) {
		this.datainsc = datainsc;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

}
