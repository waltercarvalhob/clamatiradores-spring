package com.clamatiradores.socio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.clamatiradores.socio.Socio;

public class SocioForm {

	private Integer idSocio;

	@NotBlank(message = "Nome e obrigatorio")
	private String nome;

	private String endereco;

	@Email(message = "Email invalido")
	private String email;

	private String fone;

	@NotBlank(message = "CPF e obrigatorio")
	private String cpf;

	private String rg;
	private String profissao;
	private String numclam;
	private String numcr;
	private String pai;
	private String mae;
	private String filiacao;
	private String renovacao;
	private String validade;
	private String datanasc;
	private String observacao;
	private String situacao;
	private String pagamento;

	public static SocioForm fromEntity(Socio socio) {
		SocioForm form = new SocioForm();
		form.setIdSocio(socio.getIdSocio());
		form.setNome(socio.getNome());
		form.setEndereco(socio.getEndereco());
		form.setEmail(socio.getEmail());
		form.setFone(socio.getFone());
		form.setCpf(socio.getCpf());
		form.setRg(socio.getRg());
		form.setProfissao(socio.getProfissao());
		form.setNumclam(socio.getNumclam());
		form.setNumcr(socio.getNumcr());
		form.setPai(socio.getPai());
		form.setMae(socio.getMae());
		form.setFiliacao(socio.getFiliacao());
		form.setRenovacao(socio.getRenovacao());
		form.setValidade(socio.getValidade());
		form.setDatanasc(socio.getDatanasc());
		form.setObservacao(socio.getObservacao());
		form.setSituacao(socio.getSituacao());
		form.setPagamento(socio.getPagamento());
		return form;
	}

	public void copyTo(Socio socio) {
		socio.setNome(nome);
		socio.setEndereco(endereco);
		socio.setEmail(email);
		socio.setFone(fone);
		socio.setCpf(cpf);
		socio.setRg(rg);
		socio.setProfissao(profissao);
		socio.setNumclam(numclam);
		socio.setNumcr(numcr);
		socio.setPai(pai);
		socio.setMae(mae);
		socio.setFiliacao(filiacao);
		socio.setRenovacao(renovacao);
		socio.setValidade(validade);
		socio.setDatanasc(datanasc);
		socio.setObservacao(observacao);
		socio.setSituacao(situacao);
		socio.setPagamento(pagamento);
	}

	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getNumclam() {
		return numclam;
	}

	public void setNumclam(String numclam) {
		this.numclam = numclam;
	}

	public String getNumcr() {
		return numcr;
	}

	public void setNumcr(String numcr) {
		this.numcr = numcr;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public String getFiliacao() {
		return filiacao;
	}

	public void setFiliacao(String filiacao) {
		this.filiacao = filiacao;
	}

	public String getRenovacao() {
		return renovacao;
	}

	public void setRenovacao(String renovacao) {
		this.renovacao = renovacao;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getDatanasc() {
		return datanasc;
	}

	public void setDatanasc(String datanasc) {
		this.datanasc = datanasc;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

}
