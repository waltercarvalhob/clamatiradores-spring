package com.clamatiradores.socio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Mapeia a tabela "socio" existente no banco bdsocio, criada pelo sistema legado
 * (src/dados/Socio.java + src/dao/Sociodao.java no projeto Servlet/JSP original).
 * Os campos de data (datanasc, validade, filiacao) permanecem String de proposito:
 * a coluna real ja e varchar no schema atual e esta fase nao faz migracao de schema.
 */
@Entity
@Table(name = "socio")
public class Socio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_socio")
	private Integer idSocio;

	@Column(name = "file")
	private String file;

	@Column(name = "nome")
	private String nome;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "email")
	private String email;

	@Column(name = "fone")
	private String fone;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "rg")
	private String rg;

	@Column(name = "profissao")
	private String profissao;

	@Column(name = "numclam")
	private String numclam;

	@Column(name = "numcr")
	private String numcr;

	@Column(name = "pai")
	private String pai;

	@Column(name = "mae")
	private String mae;

	@Column(name = "filiacao")
	private String filiacao;

	@Column(name = "renovacao")
	private String renovacao;

	@Column(name = "validade")
	private String validade;

	@Column(name = "datanasc")
	private String datanasc;

	@Column(name = "observacao")
	private String observacao;

	@Column(name = "situacao")
	private String situacao;

	@Column(name = "pagamento")
	private String pagamento;

	public Integer getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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
