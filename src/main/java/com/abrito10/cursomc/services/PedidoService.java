package com.abrito10.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.abrito10.cursomc.dao.ItemPedidoDAO;
import com.abrito10.cursomc.dao.PagamentoDAO;
import com.abrito10.cursomc.dao.PedidoDAO;
import com.abrito10.cursomc.domain.Cliente;
import com.abrito10.cursomc.domain.ItemPedido;
import com.abrito10.cursomc.domain.PagamentoComBoleto;
import com.abrito10.cursomc.domain.Pedido;
import com.abrito10.cursomc.domain.enums.EstadoPagamento;
import com.abrito10.cursomc.security.UserSS;
import com.abrito10.cursomc.services.exception.AuthorizationException;
import com.abrito10.cursomc.services.exception.ObjectNotFoundException;



@Service
public class PedidoService {
	
	@Autowired
	private PedidoDAO repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoDAO pagamentoDao;
	
	@Autowired
	private ItemPedidoDAO itemPedidoDao;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
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
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}