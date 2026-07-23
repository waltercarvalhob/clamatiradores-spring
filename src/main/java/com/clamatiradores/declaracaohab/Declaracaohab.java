package com.clamatiradores.declaracaohab;

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
 * Mapeia a tabela "declaracaohab" existente (src/dados/Declaracaohab.java +
 * src/dao/Declaracaohabdao.java no sistema legado) - declaracao de habitualidade,
 * com 20 grupos repetidos de (local, data do evento, treino/competicao). O primeiro
 * grupo usa as colunas sem sufixo (local/dataevento/treino_competicao), os demais
 * 19 usam sufixo numerico (local1..local19, etc) - por isso os campos Java usam
 * indice 0..19 mas o @Column do indice 0 aponta para o nome sem sufixo.
 *
 * NOTA: o codigo legado (ServerDeclaracaohabAltera.java, ~linha 187) tinha um bug de
 * copy-paste que gravava o valor de treino_competicao8 no campo base (indice 0) ao
 * atualizar - corrigido nesta migracao (ver DeclaracaohabForm.copyTo/fromEntity, que
 * mapeia cada indice da lista para o campo correspondente sem essa troca).
 */
@Entity
@Table(name = "declaracaohab")
public class Declaracaohab {

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

	@Column(name = "local")
	private String local0;
	@Column(name = "dataevento")
	private String dataevento0;
	@Column(name = "treino_competicao")
	private String treinoCompeticao0;

	@Column(name = "local1")
	private String local1;
	@Column(name = "dataevento1")
	private String dataevento1;
	@Column(name = "treino_competicao1")
	private String treinoCompeticao1;

	@Column(name = "local2")
	private String local2;
	@Column(name = "dataevento2")
	private String dataevento2;
	@Column(name = "treino_competicao2")
	private String treinoCompeticao2;

	@Column(name = "local3")
	private String local3;
	@Column(name = "dataevento3")
	private String dataevento3;
	@Column(name = "treino_competicao3")
	private String treinoCompeticao3;

	@Column(name = "local4")
	private String local4;
	@Column(name = "dataevento4")
	private String dataevento4;
	@Column(name = "treino_competicao4")
	private String treinoCompeticao4;

	@Column(name = "local5")
	private String local5;
	@Column(name = "dataevento5")
	private String dataevento5;
	@Column(name = "treino_competicao5")
	private String treinoCompeticao5;

	@Column(name = "local6")
	private String local6;
	@Column(name = "dataevento6")
	private String dataevento6;
	@Column(name = "treino_competicao6")
	private String treinoCompeticao6;

	@Column(name = "local7")
	private String local7;
	@Column(name = "dataevento7")
	private String dataevento7;
	@Column(name = "treino_competicao7")
	private String treinoCompeticao7;

	@Column(name = "local8")
	private String local8;
	@Column(name = "dataevento8")
	private String dataevento8;
	@Column(name = "treino_competicao8")
	private String treinoCompeticao8;

	@Column(name = "local9")
	private String local9;
	@Column(name = "dataevento9")
	private String dataevento9;
	@Column(name = "treino_competicao9")
	private String treinoCompeticao9;

	@Column(name = "local10")
	private String local10;
	@Column(name = "dataevento10")
	private String dataevento10;
	@Column(name = "treino_competicao10")
	private String treinoCompeticao10;

	@Column(name = "local11")
	private String local11;
	@Column(name = "dataevento11")
	private String dataevento11;
	@Column(name = "treino_competicao11")
	private String treinoCompeticao11;

	@Column(name = "local12")
	private String local12;
	@Column(name = "dataevento12")
	private String dataevento12;
	@Column(name = "treino_competicao12")
	private String treinoCompeticao12;

	@Column(name = "local13")
	private String local13;
	@Column(name = "dataevento13")
	private String dataevento13;
	@Column(name = "treino_competicao13")
	private String treinoCompeticao13;

	@Column(name = "local14")
	private String local14;
	@Column(name = "dataevento14")
	private String dataevento14;
	@Column(name = "treino_competicao14")
	private String treinoCompeticao14;

	@Column(name = "local15")
	private String local15;
	@Column(name = "dataevento15")
	private String dataevento15;
	@Column(name = "treino_competicao15")
	private String treinoCompeticao15;

	@Column(name = "local16")
	private String local16;
	@Column(name = "dataevento16")
	private String dataevento16;
	@Column(name = "treino_competicao16")
	private String treinoCompeticao16;

	@Column(name = "local17")
	private String local17;
	@Column(name = "dataevento17")
	private String dataevento17;
	@Column(name = "treino_competicao17")
	private String treinoCompeticao17;

	@Column(name = "local18")
	private String local18;
	@Column(name = "dataevento18")
	private String dataevento18;
	@Column(name = "treino_competicao18")
	private String treinoCompeticao18;

	@Column(name = "local19")
	private String local19;
	@Column(name = "dataevento19")
	private String dataevento19;
	@Column(name = "treino_competicao19")
	private String treinoCompeticao19;

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

	public String getLocal0() { return local0; }
	public void setLocal0(String v) { this.local0 = v; }
	public String getDataevento0() { return dataevento0; }
	public void setDataevento0(String v) { this.dataevento0 = v; }
	public String getTreinoCompeticao0() { return treinoCompeticao0; }
	public void setTreinoCompeticao0(String v) { this.treinoCompeticao0 = v; }

	public String getLocal1() { return local1; }
	public void setLocal1(String v) { this.local1 = v; }
	public String getDataevento1() { return dataevento1; }
	public void setDataevento1(String v) { this.dataevento1 = v; }
	public String getTreinoCompeticao1() { return treinoCompeticao1; }
	public void setTreinoCompeticao1(String v) { this.treinoCompeticao1 = v; }

	public String getLocal2() { return local2; }
	public void setLocal2(String v) { this.local2 = v; }
	public String getDataevento2() { return dataevento2; }
	public void setDataevento2(String v) { this.dataevento2 = v; }
	public String getTreinoCompeticao2() { return treinoCompeticao2; }
	public void setTreinoCompeticao2(String v) { this.treinoCompeticao2 = v; }

	public String getLocal3() { return local3; }
	public void setLocal3(String v) { this.local3 = v; }
	public String getDataevento3() { return dataevento3; }
	public void setDataevento3(String v) { this.dataevento3 = v; }
	public String getTreinoCompeticao3() { return treinoCompeticao3; }
	public void setTreinoCompeticao3(String v) { this.treinoCompeticao3 = v; }

	public String getLocal4() { return local4; }
	public void setLocal4(String v) { this.local4 = v; }
	public String getDataevento4() { return dataevento4; }
	public void setDataevento4(String v) { this.dataevento4 = v; }
	public String getTreinoCompeticao4() { return treinoCompeticao4; }
	public void setTreinoCompeticao4(String v) { this.treinoCompeticao4 = v; }

	public String getLocal5() { return local5; }
	public void setLocal5(String v) { this.local5 = v; }
	public String getDataevento5() { return dataevento5; }
	public void setDataevento5(String v) { this.dataevento5 = v; }
	public String getTreinoCompeticao5() { return treinoCompeticao5; }
	public void setTreinoCompeticao5(String v) { this.treinoCompeticao5 = v; }

	public String getLocal6() { return local6; }
	public void setLocal6(String v) { this.local6 = v; }
	public String getDataevento6() { return dataevento6; }
	public void setDataevento6(String v) { this.dataevento6 = v; }
	public String getTreinoCompeticao6() { return treinoCompeticao6; }
	public void setTreinoCompeticao6(String v) { this.treinoCompeticao6 = v; }

	public String getLocal7() { return local7; }
	public void setLocal7(String v) { this.local7 = v; }
	public String getDataevento7() { return dataevento7; }
	public void setDataevento7(String v) { this.dataevento7 = v; }
	public String getTreinoCompeticao7() { return treinoCompeticao7; }
	public void setTreinoCompeticao7(String v) { this.treinoCompeticao7 = v; }

	public String getLocal8() { return local8; }
	public void setLocal8(String v) { this.local8 = v; }
	public String getDataevento8() { return dataevento8; }
	public void setDataevento8(String v) { this.dataevento8 = v; }
	public String getTreinoCompeticao8() { return treinoCompeticao8; }
	public void setTreinoCompeticao8(String v) { this.treinoCompeticao8 = v; }

	public String getLocal9() { return local9; }
	public void setLocal9(String v) { this.local9 = v; }
	public String getDataevento9() { return dataevento9; }
	public void setDataevento9(String v) { this.dataevento9 = v; }
	public String getTreinoCompeticao9() { return treinoCompeticao9; }
	public void setTreinoCompeticao9(String v) { this.treinoCompeticao9 = v; }

	public String getLocal10() { return local10; }
	public void setLocal10(String v) { this.local10 = v; }
	public String getDataevento10() { return dataevento10; }
	public void setDataevento10(String v) { this.dataevento10 = v; }
	public String getTreinoCompeticao10() { return treinoCompeticao10; }
	public void setTreinoCompeticao10(String v) { this.treinoCompeticao10 = v; }

	public String getLocal11() { return local11; }
	public void setLocal11(String v) { this.local11 = v; }
	public String getDataevento11() { return dataevento11; }
	public void setDataevento11(String v) { this.dataevento11 = v; }
	public String getTreinoCompeticao11() { return treinoCompeticao11; }
	public void setTreinoCompeticao11(String v) { this.treinoCompeticao11 = v; }

	public String getLocal12() { return local12; }
	public void setLocal12(String v) { this.local12 = v; }
	public String getDataevento12() { return dataevento12; }
	public void setDataevento12(String v) { this.dataevento12 = v; }
	public String getTreinoCompeticao12() { return treinoCompeticao12; }
	public void setTreinoCompeticao12(String v) { this.treinoCompeticao12 = v; }

	public String getLocal13() { return local13; }
	public void setLocal13(String v) { this.local13 = v; }
	public String getDataevento13() { return dataevento13; }
	public void setDataevento13(String v) { this.dataevento13 = v; }
	public String getTreinoCompeticao13() { return treinoCompeticao13; }
	public void setTreinoCompeticao13(String v) { this.treinoCompeticao13 = v; }

	public String getLocal14() { return local14; }
	public void setLocal14(String v) { this.local14 = v; }
	public String getDataevento14() { return dataevento14; }
	public void setDataevento14(String v) { this.dataevento14 = v; }
	public String getTreinoCompeticao14() { return treinoCompeticao14; }
	public void setTreinoCompeticao14(String v) { this.treinoCompeticao14 = v; }

	public String getLocal15() { return local15; }
	public void setLocal15(String v) { this.local15 = v; }
	public String getDataevento15() { return dataevento15; }
	public void setDataevento15(String v) { this.dataevento15 = v; }
	public String getTreinoCompeticao15() { return treinoCompeticao15; }
	public void setTreinoCompeticao15(String v) { this.treinoCompeticao15 = v; }

	public String getLocal16() { return local16; }
	public void setLocal16(String v) { this.local16 = v; }
	public String getDataevento16() { return dataevento16; }
	public void setDataevento16(String v) { this.dataevento16 = v; }
	public String getTreinoCompeticao16() { return treinoCompeticao16; }
	public void setTreinoCompeticao16(String v) { this.treinoCompeticao16 = v; }

	public String getLocal17() { return local17; }
	public void setLocal17(String v) { this.local17 = v; }
	public String getDataevento17() { return dataevento17; }
	public void setDataevento17(String v) { this.dataevento17 = v; }
	public String getTreinoCompeticao17() { return treinoCompeticao17; }
	public void setTreinoCompeticao17(String v) { this.treinoCompeticao17 = v; }

	public String getLocal18() { return local18; }
	public void setLocal18(String v) { this.local18 = v; }
	public String getDataevento18() { return dataevento18; }
	public void setDataevento18(String v) { this.dataevento18 = v; }
	public String getTreinoCompeticao18() { return treinoCompeticao18; }
	public void setTreinoCompeticao18(String v) { this.treinoCompeticao18 = v; }

	public String getLocal19() { return local19; }
	public void setLocal19(String v) { this.local19 = v; }
	public String getDataevento19() { return dataevento19; }
	public void setDataevento19(String v) { this.dataevento19 = v; }
	public String getTreinoCompeticao19() { return treinoCompeticao19; }
	public void setTreinoCompeticao19(String v) { this.treinoCompeticao19 = v; }

}
