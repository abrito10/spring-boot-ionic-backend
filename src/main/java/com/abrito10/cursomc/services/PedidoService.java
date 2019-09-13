package com.abrito10.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abrito10.cursomc.dao.ClienteDAO;
import com.abrito10.cursomc.dao.ItemPedidoDAO;
import com.abrito10.cursomc.dao.PagamentoDAO;
import com.abrito10.cursomc.dao.PedidoDAO;
import com.abrito10.cursomc.domain.ItemPedido;
import com.abrito10.cursomc.domain.PagamentoComBoleto;
import com.abrito10.cursomc.domain.Pedido;
import com.abrito10.cursomc.domain.enums.EstadoPagamento;
import com.abrito10.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoDAO repo;

	@Autowired
	private PagamentoDAO pagamentoDao;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoDAO itemPedidoDao;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ClienteService clienteService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoDao.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoDao.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}
}
