package com.abrito10.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abrito10.cursomc.dao.CategoriaDAO;
import com.abrito10.cursomc.dao.CidadeDAO;
import com.abrito10.cursomc.dao.ClienteDAO;
import com.abrito10.cursomc.dao.EnderecoDAO;
import com.abrito10.cursomc.dao.EstadoDAO;
import com.abrito10.cursomc.dao.ItemPedidoDAO;
import com.abrito10.cursomc.dao.PagamentoDAO;
import com.abrito10.cursomc.dao.PedidoDAO;
import com.abrito10.cursomc.dao.ProdutoDAO;
import com.abrito10.cursomc.domain.Categoria;
import com.abrito10.cursomc.domain.Cidade;
import com.abrito10.cursomc.domain.Cliente;
import com.abrito10.cursomc.domain.Endereco;
import com.abrito10.cursomc.domain.Estado;
import com.abrito10.cursomc.domain.ItemPedido;
import com.abrito10.cursomc.domain.Pagamento;
import com.abrito10.cursomc.domain.PagamentoComBoleto;
import com.abrito10.cursomc.domain.PagamentoComCartao;
import com.abrito10.cursomc.domain.Pedido;
import com.abrito10.cursomc.domain.Produto;
import com.abrito10.cursomc.domain.enums.EstadoPagamento;
import com.abrito10.cursomc.domain.enums.TipoCliente;

@Service
public class DBService {

	@Autowired
	private CategoriaDAO categoriaDao;

	@Autowired
	private ProdutoDAO produtoDao;

	@Autowired
	private EstadoDAO estadoDao;

	@Autowired
	private CidadeDAO cidadeDao;

	@Autowired
	private ClienteDAO clienteDao;

	@Autowired
	private EnderecoDAO enderecoDao;

	@Autowired
	private PagamentoDAO pagamentoDao;

	@Autowired
	private PedidoDAO pedidoDao;

	@Autowired
	private ItemPedidoDAO itemPedidoDao;

	public void instantiateDataBase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama Mesa Banho");
		Categoria cat4 = new Categoria(null, "Eletronico");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoracao");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		categoriaDao.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoDao.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoDao.saveAll(Arrays.asList(est1, est2));
		cidadeDao.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678909", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("55101111", "999990202"));

		Endereco e1 = new Endereco(null, "Rua Flores", "2", "Apto 2", "Jardim", "01118190", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "103", "Sala 2", "Centro", "222200", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteDao.saveAll(Arrays.asList(cli1));
		enderecoDao.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/07/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 10:32"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoDao.saveAll(Arrays.asList(ped1, ped2));
		pagamentoDao.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoDao.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
