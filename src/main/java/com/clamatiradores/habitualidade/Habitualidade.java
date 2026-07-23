package com.clamatiradores.habitualidade;

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
 * Mapeia a tabela "tbfrequencia" existente (src/dados/Habitualidade.java +
 * src/dao/Habitualidadedao.java no sistema legado) - registro de habitualidade
 * (frequencia de treino/competicao) do socio, com 20 grupos repetidos de
 * (data, SIGMA, municao, evento). Os campos cpf/endereco/numcr/municao (escalares)
 * sao copias denormalizadas que ja existiam no schema legado, mantidas como estao.
 */
@Entity
@Table(name = "tbfrequencia")
public class Habitualidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_freq")
	private Integer idFreq;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_socio")
	private Socio socio;

	@Column(name = "ano")
	private String ano;
	@Column(name = "mes")
	private String mes;
	@Column(name = "datafiliacao")
	private String datafiliacao;
	@Column(name = "datavalidade")
	private String datavalidade;
	@Column(name = "nivel")
	private String nivel;
	@Column(name = "municao")
	private String municao;
	@Column(name = "treinos")
	private String treinos;
	@Column(name = "municipal")
	private String municipal;
	@Column(name = "estadual")
	private String estadual;
	@Column(name = "federal")
	private String federal;
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "numcr")
	private String numcr;
	@Column(name = "livrosis")
	private String livrosis;
	@Column(name = "folhanumregistro")
	private String folhanumregistro;
	@Column(name = "datalancamento")
	private String datalancamento;
	@Column(name = "numclam")
	private String numclam;

	@Column(name = "data1") private String data1;
	@Column(name = "data2") private String data2;
	@Column(name = "data3") private String data3;
	@Column(name = "data4") private String data4;
	@Column(name = "data5") private String data5;
	@Column(name = "data6") private String data6;
	@Column(name = "data7") private String data7;
	@Column(name = "data8") private String data8;
	@Column(name = "data9") private String data9;
	@Column(name = "data10") private String data10;
	@Column(name = "data11") private String data11;
	@Column(name = "data12") private String data12;
	@Column(name = "data13") private String data13;
	@Column(name = "data14") private String data14;
	@Column(name = "data15") private String data15;
	@Column(name = "data16") private String data16;
	@Column(name = "data17") private String data17;
	@Column(name = "data18") private String data18;
	@Column(name = "data19") private String data19;
	@Column(name = "data20") private String data20;

	@Column(name = "sigma1") private String sigma1;
	@Column(name = "sigma2") private String sigma2;
	@Column(name = "sigma3") private String sigma3;
	@Column(name = "sigma4") private String sigma4;
	@Column(name = "sigma5") private String sigma5;
	@Column(name = "sigma6") private String sigma6;
	@Column(name = "sigma7") private String sigma7;
	@Column(name = "sigma8") private String sigma8;
	@Column(name = "sigma9") private String sigma9;
	@Column(name = "sigma10") private String sigma10;
	@Column(name = "sigma11") private String sigma11;
	@Column(name = "sigma12") private String sigma12;
	@Column(name = "sigma13") private String sigma13;
	@Column(name = "sigma14") private String sigma14;
	@Column(name = "sigma15") private String sigma15;
	@Column(name = "sigma16") private String sigma16;
	@Column(name = "sigma17") private String sigma17;
	@Column(name = "sigma18") private String sigma18;
	@Column(name = "sigma19") private String sigma19;
	@Column(name = "sigma20") private String sigma20;

	@Column(name = "municao1") private String municao1;
	@Column(name = "municao2") private String municao2;
	@Column(name = "municao3") private String municao3;
	@Column(name = "municao4") private String municao4;
	@Column(name = "municao5") private String municao5;
	@Column(name = "municao6") private String municao6;
	@Column(name = "municao7") private String municao7;
	@Column(name = "municao8") private String municao8;
	@Column(name = "municao9") private String municao9;
	@Column(name = "municao10") private String municao10;
	@Column(name = "municao11") private String municao11;
	@Column(name = "municao12") private String municao12;
	@Column(name = "municao13") private String municao13;
	@Column(name = "municao14") private String municao14;
	@Column(name = "municao15") private String municao15;
	@Column(name = "municao16") private String municao16;
	@Column(name = "municao17") private String municao17;
	@Column(name = "municao18") private String municao18;
	@Column(name = "municao19") private String municao19;
	@Column(name = "municao20") private String municao20;

	@Column(name = "evento1") private String evento1;
	@Column(name = "evento2") private String evento2;
	@Column(name = "evento3") private String evento3;
	@Column(name = "evento4") private String evento4;
	@Column(name = "evento5") private String evento5;
	@Column(name = "evento6") private String evento6;
	@Column(name = "evento7") private String evento7;
	@Column(name = "evento8") private String evento8;
	@Column(name = "evento9") private String evento9;
	@Column(name = "evento10") private String evento10;
	@Column(name = "evento11") private String evento11;
	@Column(name = "evento12") private String evento12;
	@Column(name = "evento13") private String evento13;
	@Column(name = "evento14") private String evento14;
	@Column(name = "evento15") private String evento15;
	@Column(name = "evento16") private String evento16;
	@Column(name = "evento17") private String evento17;
	@Column(name = "evento18") private String evento18;
	@Column(name = "evento19") private String evento19;
	@Column(name = "evento20") private String evento20;

	public Integer getIdFreq() { return idFreq; }
	public void setIdFreq(Integer idFreq) { this.idFreq = idFreq; }
	public Socio getSocio() { return socio; }
	public void setSocio(Socio socio) { this.socio = socio; }
	public String getAno() { return ano; }
	public void setAno(String ano) { this.ano = ano; }
	public String getMes() { return mes; }
	public void setMes(String mes) { this.mes = mes; }
	public String getDatafiliacao() { return datafiliacao; }
	public void setDatafiliacao(String datafiliacao) { this.datafiliacao = datafiliacao; }
	public String getDatavalidade() { return datavalidade; }
	public void setDatavalidade(String datavalidade) { this.datavalidade = datavalidade; }
	public String getNivel() { return nivel; }
	public void setNivel(String nivel) { this.nivel = nivel; }
	public String getMunicao() { return municao; }
	public void setMunicao(String municao) { this.municao = municao; }
	public String getTreinos() { return treinos; }
	public void setTreinos(String treinos) { this.treinos = treinos; }
	public String getMunicipal() { return municipal; }
	public void setMunicipal(String municipal) { this.municipal = municipal; }
	public String getEstadual() { return estadual; }
	public void setEstadual(String estadual) { this.estadual = estadual; }
	public String getFederal() { return federal; }
	public void setFederal(String federal) { this.federal = federal; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	public String getEndereco() { return endereco; }
	public void setEndereco(String endereco) { this.endereco = endereco; }
	public String getNumcr() { return numcr; }
	public void setNumcr(String numcr) { this.numcr = numcr; }
	public String getLivrosis() { return livrosis; }
	public void setLivrosis(String livrosis) { this.livrosis = livrosis; }
	public String getFolhanumregistro() { return folhanumregistro; }
	public void setFolhanumregistro(String folhanumregistro) { this.folhanumregistro = folhanumregistro; }
	public String getDatalancamento() { return datalancamento; }
	public void setDatalancamento(String datalancamento) { this.datalancamento = datalancamento; }
	public String getNumclam() { return numclam; }
	public void setNumclam(String numclam) { this.numclam = numclam; }

	public String getData1() { return data1; } public void setData1(String v) { this.data1 = v; }
	public String getData2() { return data2; } public void setData2(String v) { this.data2 = v; }
	public String getData3() { return data3; } public void setData3(String v) { this.data3 = v; }
	public String getData4() { return data4; } public void setData4(String v) { this.data4 = v; }
	public String getData5() { return data5; } public void setData5(String v) { this.data5 = v; }
	public String getData6() { return data6; } public void setData6(String v) { this.data6 = v; }
	public String getData7() { return data7; } public void setData7(String v) { this.data7 = v; }
	public String getData8() { return data8; } public void setData8(String v) { this.data8 = v; }
	public String getData9() { return data9; } public void setData9(String v) { this.data9 = v; }
	public String getData10() { return data10; } public void setData10(String v) { this.data10 = v; }
	public String getData11() { return data11; } public void setData11(String v) { this.data11 = v; }
	public String getData12() { return data12; } public void setData12(String v) { this.data12 = v; }
	public String getData13() { return data13; } public void setData13(String v) { this.data13 = v; }
	public String getData14() { return data14; } public void setData14(String v) { this.data14 = v; }
	public String getData15() { return data15; } public void setData15(String v) { this.data15 = v; }
	public String getData16() { return data16; } public void setData16(String v) { this.data16 = v; }
	public String getData17() { return data17; } public void setData17(String v) { this.data17 = v; }
	public String getData18() { return data18; } public void setData18(String v) { this.data18 = v; }
	public String getData19() { return data19; } public void setData19(String v) { this.data19 = v; }
	public String getData20() { return data20; } public void setData20(String v) { this.data20 = v; }

	public String getSigma1() { return sigma1; } public void setSigma1(String v) { this.sigma1 = v; }
	public String getSigma2() { return sigma2; } public void setSigma2(String v) { this.sigma2 = v; }
	public String getSigma3() { return sigma3; } public void setSigma3(String v) { this.sigma3 = v; }
	public String getSigma4() { return sigma4; } public void setSigma4(String v) { this.sigma4 = v; }
	public String getSigma5() { return sigma5; } public void setSigma5(String v) { this.sigma5 = v; }
	public String getSigma6() { return sigma6; } public void setSigma6(String v) { this.sigma6 = v; }
	public String getSigma7() { return sigma7; } public void setSigma7(String v) { this.sigma7 = v; }
	public String getSigma8() { return sigma8; } public void setSigma8(String v) { this.sigma8 = v; }
	public String getSigma9() { return sigma9; } public void setSigma9(String v) { this.sigma9 = v; }
	public String getSigma10() { return sigma10; } public void setSigma10(String v) { this.sigma10 = v; }
	public String getSigma11() { return sigma11; } public void setSigma11(String v) { this.sigma11 = v; }
	public String getSigma12() { return sigma12; } public void setSigma12(String v) { this.sigma12 = v; }
	public String getSigma13() { return sigma13; } public void setSigma13(String v) { this.sigma13 = v; }
	public String getSigma14() { return sigma14; } public void setSigma14(String v) { this.sigma14 = v; }
	public String getSigma15() { return sigma15; } public void setSigma15(String v) { this.sigma15 = v; }
	public String getSigma16() { return sigma16; } public void setSigma16(String v) { this.sigma16 = v; }
	public String getSigma17() { return sigma17; } public void setSigma17(String v) { this.sigma17 = v; }
	public String getSigma18() { return sigma18; } public void setSigma18(String v) { this.sigma18 = v; }
	public String getSigma19() { return sigma19; } public void setSigma19(String v) { this.sigma19 = v; }
	public String getSigma20() { return sigma20; } public void setSigma20(String v) { this.sigma20 = v; }

	public String getMunicao1() { return municao1; } public void setMunicao1(String v) { this.municao1 = v; }
	public String getMunicao2() { return municao2; } public void setMunicao2(String v) { this.municao2 = v; }
	public String getMunicao3() { return municao3; } public void setMunicao3(String v) { this.municao3 = v; }
	public String getMunicao4() { return municao4; } public void setMunicao4(String v) { this.municao4 = v; }
	public String getMunicao5() { return municao5; } public void setMunicao5(String v) { this.municao5 = v; }
	public String getMunicao6() { return municao6; } public void setMunicao6(String v) { this.municao6 = v; }
	public String getMunicao7() { return municao7; } public void setMunicao7(String v) { this.municao7 = v; }
	public String getMunicao8() { return municao8; } public void setMunicao8(String v) { this.municao8 = v; }
	public String getMunicao9() { return municao9; } public void setMunicao9(String v) { this.municao9 = v; }
	public String getMunicao10() { return municao10; } public void setMunicao10(String v) { this.municao10 = v; }
	public String getMunicao11() { return municao11; } public void setMunicao11(String v) { this.municao11 = v; }
	public String getMunicao12() { return municao12; } public void setMunicao12(String v) { this.municao12 = v; }
	public String getMunicao13() { return municao13; } public void setMunicao13(String v) { this.municao13 = v; }
	public String getMunicao14() { return municao14; } public void setMunicao14(String v) { this.municao14 = v; }
	public String getMunicao15() { return municao15; } public void setMunicao15(String v) { this.municao15 = v; }
	public String getMunicao16() { return municao16; } public void setMunicao16(String v) { this.municao16 = v; }
	public String getMunicao17() { return municao17; } public void setMunicao17(String v) { this.municao17 = v; }
	public String getMunicao18() { return municao18; } public void setMunicao18(String v) { this.municao18 = v; }
	public String getMunicao19() { return municao19; } public void setMunicao19(String v) { this.municao19 = v; }
	public String getMunicao20() { return municao20; } public void setMunicao20(String v) { this.municao20 = v; }

	public String getEvento1() { return evento1; } public void setEvento1(String v) { this.evento1 = v; }
	public String getEvento2() { return evento2; } public void setEvento2(String v) { this.evento2 = v; }
	public String getEvento3() { return evento3; } public void setEvento3(String v) { this.evento3 = v; }
	public String getEvento4() { return evento4; } public void setEvento4(String v) { this.evento4 = v; }
	public String getEvento5() { return evento5; } public void setEvento5(String v) { this.evento5 = v; }
	public String getEvento6() { return evento6; } public void setEvento6(String v) { this.evento6 = v; }
	public String getEvento7() { return evento7; } public void setEvento7(String v) { this.evento7 = v; }
	public String getEvento8() { return evento8; } public void setEvento8(String v) { this.evento8 = v; }
	public String getEvento9() { return evento9; } public void setEvento9(String v) { this.evento9 = v; }
	public String getEvento10() { return evento10; } public void setEvento10(String v) { this.evento10 = v; }
	public String getEvento11() { return evento11; } public void setEvento11(String v) { this.evento11 = v; }
	public String getEvento12() { return evento12; } public void setEvento12(String v) { this.evento12 = v; }
	public String getEvento13() { return evento13; } public void setEvento13(String v) { this.evento13 = v; }
	public String getEvento14() { return evento14; } public void setEvento14(String v) { this.evento14 = v; }
	public String getEvento15() { return evento15; } public void setEvento15(String v) { this.evento15 = v; }
	public String getEvento16() { return evento16; } public void setEvento16(String v) { this.evento16 = v; }
	public String getEvento17() { return evento17; } public void setEvento17(String v) { this.evento17 = v; }
	public String getEvento18() { return evento18; } public void setEvento18(String v) { this.evento18 = v; }
	public String getEvento19() { return evento19; } public void setEvento19(String v) { this.evento19 = v; }
	public String getEvento20() { return evento20; } public void setEvento20(String v) { this.evento20 = v; }

}
