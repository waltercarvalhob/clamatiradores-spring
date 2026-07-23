package com.clamatiradores.declaracaomodprova;

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
 * Mapeia a tabela "declaracaomodprova" existente (src/dados/DeclaracaoModProva.java +
 * src/dao/DeclaracaoModProvadao.java no sistema legado) - declaracao de modalidade de prova.
 */
@Entity
@Table(name = "declaracaomodprova")
public class DeclaracaoModProva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_decmodprova")
	private Integer idDecmodprova;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_socio")
	private Socio socio;

	@Column(name = "dia")
	private String dia;

	@Column(name = "mes")
	private String mes;

	@Column(name = "ano")
	private String ano;

	@Column(name = "dataatual")
	private String dataatual;

	@Column(name = "dataemissao")
	private String dataemissao;

	@Column(name = "datavalidade")
	private String datavalidade;

	public Integer getIdDecmodprova() {
		return idDecmodprova;
	}

	public void setIdDecmodprova(Integer idDecmodprova) {
		this.idDecmodprova = idDecmodprova;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
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
