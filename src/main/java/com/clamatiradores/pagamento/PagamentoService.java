package com.clamatiradores.pagamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clamatiradores.pagamento.dto.PagamentoForm;

public interface PagamentoService {

	Page<Pagamento> search(String nome, String cpf, Pageable pageable);

	Pagamento findById(Integer id);

	Pagamento create(PagamentoForm form);

	Pagamento update(Integer id, PagamentoForm form);

	void delete(Integer id);

}
