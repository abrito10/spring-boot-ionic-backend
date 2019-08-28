package com.abrito10.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner{
	
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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));


		
		categoriaDao.saveAll(Arrays.asList(cat1,cat2));
		produtoDao.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoDao.saveAll(Arrays.asList(est1,est2));
		cidadeDao.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678909", TipoCliente.PESSOAFISICA);	
		cli1.getTelefones().addAll(Arrays.asList("55101111", "999990202"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "2", "Apto 2", "Jardim", "01118190", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "103", "Sala 2", "Centro", "222200", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteDao.saveAll(Arrays.asList(cli1));
		enderecoDao.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/07/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 10:32"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoDao.saveAll(Arrays.asList(ped1,ped2));
		pagamentoDao.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);	
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);	
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoDao.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
