package com.clamatiradores.declaracaohab.dto;

import java.util.ArrayList;
import java.util.List;

import com.clamatiradores.declaracaohab.Declaracaohab;

/**
 * DTO para a tela de Declaracao de Habitualidade. Os 20 grupos repetidos
 * (local/data do evento/treino-competicao) da entidade viram uma lista de 20
 * EventoRow para renderizar como uma grade na tela, em vez de 60 campos soltos.
 *
 * fromEntity()/copyTo() fazem o mapeamento indice-a-indice explicito com o campo
 * correspondente da entidade - ao contrario do bug do codigo legado
 * (ServerDeclaracaohabAltera.java, que gravava o valor do indice 8 no campo base
 * por um copy-paste), aqui o indice 8 da lista sempre corresponde a
 * treinoCompeticao8/dataevento8/local8 da entidade, nunca ao indice 0.
 */
public class DeclaracaohabForm {

	public static final int TOTAL_EVENTOS = 20;

	private Integer idDec;
	private Integer idSocio;
	private String socioNome;
	private String socioCpf;

	private String datainsc;
	private String dia;
	private String mes;
	private String ano;
	private String dataemissao;
	private String datavalidade;

	private List<EventoRow> eventos = novaListaVazia();

	public static List<EventoRow> novaListaVazia() {
		List<EventoRow> lista = new ArrayList<>(TOTAL_EVENTOS);
		for (int i = 0; i < TOTAL_EVENTOS; i++) {
			lista.add(new EventoRow());
		}
		return lista;
	}

	public static DeclaracaohabForm fromEntity(Declaracaohab d) {
		DeclaracaohabForm form = new DeclaracaohabForm();
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

		List<EventoRow> eventos = form.getEventos();
		setRow(eventos, 0, d.getLocal0(), d.getDataevento0(), d.getTreinoCompeticao0());
		setRow(eventos, 1, d.getLocal1(), d.getDataevento1(), d.getTreinoCompeticao1());
		setRow(eventos, 2, d.getLocal2(), d.getDataevento2(), d.getTreinoCompeticao2());
		setRow(eventos, 3, d.getLocal3(), d.getDataevento3(), d.getTreinoCompeticao3());
		setRow(eventos, 4, d.getLocal4(), d.getDataevento4(), d.getTreinoCompeticao4());
		setRow(eventos, 5, d.getLocal5(), d.getDataevento5(), d.getTreinoCompeticao5());
		setRow(eventos, 6, d.getLocal6(), d.getDataevento6(), d.getTreinoCompeticao6());
		setRow(eventos, 7, d.getLocal7(), d.getDataevento7(), d.getTreinoCompeticao7());
		setRow(eventos, 8, d.getLocal8(), d.getDataevento8(), d.getTreinoCompeticao8());
		setRow(eventos, 9, d.getLocal9(), d.getDataevento9(), d.getTreinoCompeticao9());
		setRow(eventos, 10, d.getLocal10(), d.getDataevento10(), d.getTreinoCompeticao10());
		setRow(eventos, 11, d.getLocal11(), d.getDataevento11(), d.getTreinoCompeticao11());
		setRow(eventos, 12, d.getLocal12(), d.getDataevento12(), d.getTreinoCompeticao12());
		setRow(eventos, 13, d.getLocal13(), d.getDataevento13(), d.getTreinoCompeticao13());
		setRow(eventos, 14, d.getLocal14(), d.getDataevento14(), d.getTreinoCompeticao14());
		setRow(eventos, 15, d.getLocal15(), d.getDataevento15(), d.getTreinoCompeticao15());
		setRow(eventos, 16, d.getLocal16(), d.getDataevento16(), d.getTreinoCompeticao16());
		setRow(eventos, 17, d.getLocal17(), d.getDataevento17(), d.getTreinoCompeticao17());
		setRow(eventos, 18, d.getLocal18(), d.getDataevento18(), d.getTreinoCompeticao18());
		setRow(eventos, 19, d.getLocal19(), d.getDataevento19(), d.getTreinoCompeticao19());
		return form;
	}

	private static void setRow(List<EventoRow> eventos, int index, String local, String dataevento, String treino) {
		EventoRow row = eventos.get(index);
		row.setLocal(local);
		row.setDataevento(dataevento);
		row.setTreinoCompeticao(treino);
	}

	public void copyTo(Declaracaohab d) {
		d.setDatainsc(datainsc);
		d.setDia(dia);
		d.setMes(mes);
		d.setAno(ano);
		d.setDataemissao(dataemissao);
		d.setDatavalidade(datavalidade);

		List<EventoRow> ev = garantirTamanho();
		d.setLocal0(ev.get(0).getLocal()); d.setDataevento0(ev.get(0).getDataevento()); d.setTreinoCompeticao0(ev.get(0).getTreinoCompeticao());
		d.setLocal1(ev.get(1).getLocal()); d.setDataevento1(ev.get(1).getDataevento()); d.setTreinoCompeticao1(ev.get(1).getTreinoCompeticao());
		d.setLocal2(ev.get(2).getLocal()); d.setDataevento2(ev.get(2).getDataevento()); d.setTreinoCompeticao2(ev.get(2).getTreinoCompeticao());
		d.setLocal3(ev.get(3).getLocal()); d.setDataevento3(ev.get(3).getDataevento()); d.setTreinoCompeticao3(ev.get(3).getTreinoCompeticao());
		d.setLocal4(ev.get(4).getLocal()); d.setDataevento4(ev.get(4).getDataevento()); d.setTreinoCompeticao4(ev.get(4).getTreinoCompeticao());
		d.setLocal5(ev.get(5).getLocal()); d.setDataevento5(ev.get(5).getDataevento()); d.setTreinoCompeticao5(ev.get(5).getTreinoCompeticao());
		d.setLocal6(ev.get(6).getLocal()); d.setDataevento6(ev.get(6).getDataevento()); d.setTreinoCompeticao6(ev.get(6).getTreinoCompeticao());
		d.setLocal7(ev.get(7).getLocal()); d.setDataevento7(ev.get(7).getDataevento()); d.setTreinoCompeticao7(ev.get(7).getTreinoCompeticao());
		d.setLocal8(ev.get(8).getLocal()); d.setDataevento8(ev.get(8).getDataevento()); d.setTreinoCompeticao8(ev.get(8).getTreinoCompeticao());
		d.setLocal9(ev.get(9).getLocal()); d.setDataevento9(ev.get(9).getDataevento()); d.setTreinoCompeticao9(ev.get(9).getTreinoCompeticao());
		d.setLocal10(ev.get(10).getLocal()); d.setDataevento10(ev.get(10).getDataevento()); d.setTreinoCompeticao10(ev.get(10).getTreinoCompeticao());
		d.setLocal11(ev.get(11).getLocal()); d.setDataevento11(ev.get(11).getDataevento()); d.setTreinoCompeticao11(ev.get(11).getTreinoCompeticao());
		d.setLocal12(ev.get(12).getLocal()); d.setDataevento12(ev.get(12).getDataevento()); d.setTreinoCompeticao12(ev.get(12).getTreinoCompeticao());
		d.setLocal13(ev.get(13).getLocal()); d.setDataevento13(ev.get(13).getDataevento()); d.setTreinoCompeticao13(ev.get(13).getTreinoCompeticao());
		d.setLocal14(ev.get(14).getLocal()); d.setDataevento14(ev.get(14).getDataevento()); d.setTreinoCompeticao14(ev.get(14).getTreinoCompeticao());
		d.setLocal15(ev.get(15).getLocal()); d.setDataevento15(ev.get(15).getDataevento()); d.setTreinoCompeticao15(ev.get(15).getTreinoCompeticao());
		d.setLocal16(ev.get(16).getLocal()); d.setDataevento16(ev.get(16).getDataevento()); d.setTreinoCompeticao16(ev.get(16).getTreinoCompeticao());
		d.setLocal17(ev.get(17).getLocal()); d.setDataevento17(ev.get(17).getDataevento()); d.setTreinoCompeticao17(ev.get(17).getTreinoCompeticao());
		d.setLocal18(ev.get(18).getLocal()); d.setDataevento18(ev.get(18).getDataevento()); d.setTreinoCompeticao18(ev.get(18).getTreinoCompeticao());
		d.setLocal19(ev.get(19).getLocal()); d.setDataevento19(ev.get(19).getDataevento()); d.setTreinoCompeticao19(ev.get(19).getTreinoCompeticao());
	}

	private List<EventoRow> garantirTamanho() {
		while (eventos.size() < TOTAL_EVENTOS) {
			eventos.add(new EventoRow());
		}
		return eventos;
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

	public List<EventoRow> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoRow> eventos) {
		this.eventos = eventos;
	}

}
