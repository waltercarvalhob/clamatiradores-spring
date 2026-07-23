package com.clamatiradores.pagamento;

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
 * Mapeia a tabela "pagamento" existente (src/dados/Pagamento.java + src/dao/Pagamentodao.java
 * no sistema legado). O campo id_socio vira uma associacao @ManyToOne para permitir
 * pesquisar/exibir pelo nome do socio em vez do id cru.
 */
@Entity
@Table(name = "pagamento")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pag")
	private Integer idPag;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_socio")
	private Socio socio;

	@Column(name = "pagamento")
	private String pagamento;

	public Integer getIdPag() {
		return idPag;
	}

	public void setIdPag(Integer idPag) {
		this.idPag = idPag;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

}
