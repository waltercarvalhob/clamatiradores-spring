package com.clamatiradores.socio.dto;

import com.clamatiradores.socio.Socio;

/**
 * Envolve um Socio com a informacao (calculada em Java, nao no banco) de se a
 * validade ja passou - usada para destacar a linha na tela (vermelho/laranja),
 * mesma logica visual das paginas VencimentoPorNome/Data*.jsp legadas.
 */
public class VencimentoItem {

	private final Socio socio;
	private final boolean vencido;

	public VencimentoItem(Socio socio, boolean vencido) {
		this.socio = socio;
		this.vencido = vencido;
	}

	public Socio getSocio() {
		return socio;
	}

	public boolean isVencido() {
		return vencido;
	}

}
