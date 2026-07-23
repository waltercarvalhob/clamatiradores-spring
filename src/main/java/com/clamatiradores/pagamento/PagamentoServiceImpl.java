package com.clamatiradores.pagamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clamatiradores.pagamento.dto.PagamentoForm;
import com.clamatiradores.socio.Socio;
import com.clamatiradores.socio.SocioService;

@Service
@Transactional(readOnly = true)
public class PagamentoServiceImpl implements PagamentoService {

	private final PagamentoRepository pagamentoRepository;
	private final SocioService socioService;

	public PagamentoServiceImpl(PagamentoRepository pagamentoRepository, SocioService socioService) {
		this.pagamentoRepository = pagamentoRepository;
		this.socioService = socioService;
	}

	@Override
	public Page<Pagamento> search(String nome, String cpf, Pageable pageable) {
		return pagamentoRepository.findAll(PagamentoSpecifications.fromCriteria(nome, cpf), pageable);
	}

	@Override
	public Pagamento findById(Integer id) {
		return pagamentoRepository.findById(id).orElseThrow(() -> new PagamentoNotFoundException(id));
	}

	@Override
	@Transactional
	public Pagamento create(PagamentoForm form) {
		Socio socio = socioService.findById(form.getIdSocio());
		Pagamento pagamento = new Pagamento();
		pagamento.setSocio(socio);
		pagamento.setPagamento(form.getPagamento());
		return pagamentoRepository.save(pagamento);
	}

	@Override
	@Transactional
	public Pagamento update(Integer id, PagamentoForm form) {
		Pagamento pagamento = findById(id);
		pagamento.setPagamento(form.getPagamento());
		return pagamentoRepository.save(pagamento);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if (!pagamentoRepository.existsById(id)) {
			throw new PagamentoNotFoundException(id);
		}
		pagamentoRepository.deleteById(id);
	}

}
