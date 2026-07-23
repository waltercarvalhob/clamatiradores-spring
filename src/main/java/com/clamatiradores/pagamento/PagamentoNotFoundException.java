package com.clamatiradores.pagamento;

import com.clamatiradores.common.NotFoundException;

public class PagamentoNotFoundException extends NotFoundException {

	public PagamentoNotFoundException(Integer id) {
		super("Pagamento nao encontrado: id_pag=" + id);
	}

}
