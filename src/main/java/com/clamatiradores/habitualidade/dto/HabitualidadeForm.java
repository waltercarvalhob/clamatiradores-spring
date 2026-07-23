package com.clamatiradores.habitualidade.dto;

import java.util.ArrayList;
import java.util.List;

import com.clamatiradores.habitualidade.Habitualidade;

/**
 * DTO para a tela de Habitualidade (tabela tbfrequencia). Os 4 grupos de 20 colunas
 * repetidas (data1..20, sigma1..20, municao1..20, evento1..20) viram uma lista de 20
 * RegistroRow para renderizar como uma grade unica na tela, em vez de 80 campos soltos.
 * O indice 0 da lista corresponde ao sufixo 1 das colunas (data1/sigma1/...), o indice
 * 19 corresponde ao sufixo 20.
 */
public class HabitualidadeForm {

	public static final int TOTAL_REGISTROS = 20;

	private Integer idFreq;
	private Integer idSocio;
	private String socioNome;
	private String socioCpf;

	private String ano;
	private String mes;
	private String datafiliacao;
	private String datavalidade;
	private String nivel;
	private String municao;
	private String treinos;
	private String municipal;
	private String estadual;
	private String federal;
	private String cpf;
	private String endereco;
	private String numcr;
	private String livrosis;
	private String folhanumregistro;
	private String datalancamento;
	private String numclam;

	private List<RegistroRow> registros = novaListaVazia();

	public static List<RegistroRow> novaListaVazia() {
		List<RegistroRow> lista = new ArrayList<>(TOTAL_REGISTROS);
		for (int i = 0; i < TOTAL_REGISTROS; i++) {
			lista.add(new RegistroRow());
		}
		return lista;
	}

	public static HabitualidadeForm fromEntity(Habitualidade h) {
		HabitualidadeForm form = new HabitualidadeForm();
		form.setIdFreq(h.getIdFreq());
		form.setIdSocio(h.getSocio().getIdSocio());
		form.setSocioNome(h.getSocio().getNome());
		form.setSocioCpf(h.getSocio().getCpf());
		form.setAno(h.getAno());
		form.setMes(h.getMes());
		form.setDatafiliacao(h.getDatafiliacao());
		form.setDatavalidade(h.getDatavalidade());
		form.setNivel(h.getNivel());
		form.setMunicao(h.getMunicao());
		form.setTreinos(h.getTreinos());
		form.setMunicipal(h.getMunicipal());
		form.setEstadual(h.getEstadual());
		form.setFederal(h.getFederal());
		form.setCpf(h.getCpf());
		form.setEndereco(h.getEndereco());
		form.setNumcr(h.getNumcr());
		form.setLivrosis(h.getLivrosis());
		form.setFolhanumregistro(h.getFolhanumregistro());
		form.setDatalancamento(h.getDatalancamento());
		form.setNumclam(h.getNumclam());

		List<RegistroRow> r = form.getRegistros();
		setRow(r, 0, h.getData1(), h.getSigma1(), h.getMunicao1(), h.getEvento1());
		setRow(r, 1, h.getData2(), h.getSigma2(), h.getMunicao2(), h.getEvento2());
		setRow(r, 2, h.getData3(), h.getSigma3(), h.getMunicao3(), h.getEvento3());
		setRow(r, 3, h.getData4(), h.getSigma4(), h.getMunicao4(), h.getEvento4());
		setRow(r, 4, h.getData5(), h.getSigma5(), h.getMunicao5(), h.getEvento5());
		setRow(r, 5, h.getData6(), h.getSigma6(), h.getMunicao6(), h.getEvento6());
		setRow(r, 6, h.getData7(), h.getSigma7(), h.getMunicao7(), h.getEvento7());
		setRow(r, 7, h.getData8(), h.getSigma8(), h.getMunicao8(), h.getEvento8());
		setRow(r, 8, h.getData9(), h.getSigma9(), h.getMunicao9(), h.getEvento9());
		setRow(r, 9, h.getData10(), h.getSigma10(), h.getMunicao10(), h.getEvento10());
		setRow(r, 10, h.getData11(), h.getSigma11(), h.getMunicao11(), h.getEvento11());
		setRow(r, 11, h.getData12(), h.getSigma12(), h.getMunicao12(), h.getEvento12());
		setRow(r, 12, h.getData13(), h.getSigma13(), h.getMunicao13(), h.getEvento13());
		setRow(r, 13, h.getData14(), h.getSigma14(), h.getMunicao14(), h.getEvento14());
		setRow(r, 14, h.getData15(), h.getSigma15(), h.getMunicao15(), h.getEvento15());
		setRow(r, 15, h.getData16(), h.getSigma16(), h.getMunicao16(), h.getEvento16());
		setRow(r, 16, h.getData17(), h.getSigma17(), h.getMunicao17(), h.getEvento17());
		setRow(r, 17, h.getData18(), h.getSigma18(), h.getMunicao18(), h.getEvento18());
		setRow(r, 18, h.getData19(), h.getSigma19(), h.getMunicao19(), h.getEvento19());
		setRow(r, 19, h.getData20(), h.getSigma20(), h.getMunicao20(), h.getEvento20());
		return form;
	}

	private static void setRow(List<RegistroRow> r, int index, String data, String sigma, String municao, String evento) {
		RegistroRow row = r.get(index);
		row.setData(data);
		row.setSigma(sigma);
		row.setMunicao(municao);
		row.setEvento(evento);
	}

	public void copyTo(Habitualidade h) {
		h.setAno(ano);
		h.setMes(mes);
		h.setDatafiliacao(datafiliacao);
		h.setDatavalidade(datavalidade);
		h.setNivel(nivel);
		h.setMunicao(municao);
		h.setTreinos(treinos);
		h.setMunicipal(municipal);
		h.setEstadual(estadual);
		h.setFederal(federal);
		h.setCpf(cpf);
		h.setEndereco(endereco);
		h.setNumcr(numcr);
		h.setLivrosis(livrosis);
		h.setFolhanumregistro(folhanumregistro);
		h.setDatalancamento(datalancamento);
		h.setNumclam(numclam);

		List<RegistroRow> r = garantirTamanho();
		h.setData1(r.get(0).getData()); h.setSigma1(r.get(0).getSigma()); h.setMunicao1(r.get(0).getMunicao()); h.setEvento1(r.get(0).getEvento());
		h.setData2(r.get(1).getData()); h.setSigma2(r.get(1).getSigma()); h.setMunicao2(r.get(1).getMunicao()); h.setEvento2(r.get(1).getEvento());
		h.setData3(r.get(2).getData()); h.setSigma3(r.get(2).getSigma()); h.setMunicao3(r.get(2).getMunicao()); h.setEvento3(r.get(2).getEvento());
		h.setData4(r.get(3).getData()); h.setSigma4(r.get(3).getSigma()); h.setMunicao4(r.get(3).getMunicao()); h.setEvento4(r.get(3).getEvento());
		h.setData5(r.get(4).getData()); h.setSigma5(r.get(4).getSigma()); h.setMunicao5(r.get(4).getMunicao()); h.setEvento5(r.get(4).getEvento());
		h.setData6(r.get(5).getData()); h.setSigma6(r.get(5).getSigma()); h.setMunicao6(r.get(5).getMunicao()); h.setEvento6(r.get(5).getEvento());
		h.setData7(r.get(6).getData()); h.setSigma7(r.get(6).getSigma()); h.setMunicao7(r.get(6).getMunicao()); h.setEvento7(r.get(6).getEvento());
		h.setData8(r.get(7).getData()); h.setSigma8(r.get(7).getSigma()); h.setMunicao8(r.get(7).getMunicao()); h.setEvento8(r.get(7).getEvento());
		h.setData9(r.get(8).getData()); h.setSigma9(r.get(8).getSigma()); h.setMunicao9(r.get(8).getMunicao()); h.setEvento9(r.get(8).getEvento());
		h.setData10(r.get(9).getData()); h.setSigma10(r.get(9).getSigma()); h.setMunicao10(r.get(9).getMunicao()); h.setEvento10(r.get(9).getEvento());
		h.setData11(r.get(10).getData()); h.setSigma11(r.get(10).getSigma()); h.setMunicao11(r.get(10).getMunicao()); h.setEvento11(r.get(10).getEvento());
		h.setData12(r.get(11).getData()); h.setSigma12(r.get(11).getSigma()); h.setMunicao12(r.get(11).getMunicao()); h.setEvento12(r.get(11).getEvento());
		h.setData13(r.get(12).getData()); h.setSigma13(r.get(12).getSigma()); h.setMunicao13(r.get(12).getMunicao()); h.setEvento13(r.get(12).getEvento());
		h.setData14(r.get(13).getData()); h.setSigma14(r.get(13).getSigma()); h.setMunicao14(r.get(13).getMunicao()); h.setEvento14(r.get(13).getEvento());
		h.setData15(r.get(14).getData()); h.setSigma15(r.get(14).getSigma()); h.setMunicao15(r.get(14).getMunicao()); h.setEvento15(r.get(14).getEvento());
		h.setData16(r.get(15).getData()); h.setSigma16(r.get(15).getSigma()); h.setMunicao16(r.get(15).getMunicao()); h.setEvento16(r.get(15).getEvento());
		h.setData17(r.get(16).getData()); h.setSigma17(r.get(16).getSigma()); h.setMunicao17(r.get(16).getMunicao()); h.setEvento17(r.get(16).getEvento());
		h.setData18(r.get(17).getData()); h.setSigma18(r.get(17).getSigma()); h.setMunicao18(r.get(17).getMunicao()); h.setEvento18(r.get(17).getEvento());
		h.setData19(r.get(18).getData()); h.setSigma19(r.get(18).getSigma()); h.setMunicao19(r.get(18).getMunicao()); h.setEvento19(r.get(18).getEvento());
		h.setData20(r.get(19).getData()); h.setSigma20(r.get(19).getSigma()); h.setMunicao20(r.get(19).getMunicao()); h.setEvento20(r.get(19).getEvento());
	}

	private List<RegistroRow> garantirTamanho() {
		while (registros.size() < TOTAL_REGISTROS) {
			registros.add(new RegistroRow());
		}
		return registros;
	}

	public Integer getIdFreq() { return idFreq; }
	public void setIdFreq(Integer idFreq) { this.idFreq = idFreq; }
	public Integer getIdSocio() { return idSocio; }
	public void setIdSocio(Integer idSocio) { this.idSocio = idSocio; }
	public String getSocioNome() { return socioNome; }
	public void setSocioNome(String socioNome) { this.socioNome = socioNome; }
	public String getSocioCpf() { return socioCpf; }
	public void setSocioCpf(String socioCpf) { this.socioCpf = socioCpf; }
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
	public List<RegistroRow> getRegistros() { return registros; }
	public void setRegistros(List<RegistroRow> registros) { this.registros = registros; }

}
