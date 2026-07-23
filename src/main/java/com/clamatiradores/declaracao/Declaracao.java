package com.clamatiradores.declaracao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.clamatiradores.socio.Socio;

/**
 * Mapeia a tabela "declaracao" existente (src/dados/Declaracao.java +
 * src/dao/Declaracaodao.java no sistema legado) - declaracao de filiacao do socio.
 */
@Entity
@Table(name = "declaracao")
public class Declaracao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dec")
	private Integer idDec;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_socio")
	private Socio socio;

	@Column(name = "datainsc")
	private String datainsc;

	@Column(name = "dia")
	private String dia;

	@Column(name = "mes")
	private String mes;

	@Column(name = "ano")
	private String ano;

	@Column(name = "dataemissao")
	private String dataemissao;

	@Column(name = "datavalidade")
	private String datavalidade;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "cr")
	private String cr;

	public Integer getIdDec() {
		return idDec;
	}

	public void setIdDec(Integer idDec) {
		this.idDec = idDec;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
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
