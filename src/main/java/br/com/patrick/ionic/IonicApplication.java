package br.com.patrick.ionic;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.patrick.ionic.domain.Categoria;
import br.com.patrick.ionic.domain.Cidade;
import br.com.patrick.ionic.domain.Cliente;
import br.com.patrick.ionic.domain.Endereco;
import br.com.patrick.ionic.domain.Estado;
import br.com.patrick.ionic.domain.ItemPedido;
import br.com.patrick.ionic.domain.Pagamento;
import br.com.patrick.ionic.domain.PagamentoComBoleto;
import br.com.patrick.ionic.domain.PagamentoComCartao;
import br.com.patrick.ionic.domain.Pedido;
import br.com.patrick.ionic.domain.Produto;
import br.com.patrick.ionic.domain.enuns.EstadoPagamento;
import br.com.patrick.ionic.domain.enuns.TipoCliente;
import br.com.patrick.ionic.repositories.CategoriaRepository;
import br.com.patrick.ionic.repositories.CidadeRepository;
import br.com.patrick.ionic.repositories.ClienteRepository;
import br.com.patrick.ionic.repositories.EnderecoRepository;
import br.com.patrick.ionic.repositories.EstadoRepository;
import br.com.patrick.ionic.repositories.ItemPedidoRepository;
import br.com.patrick.ionic.repositories.PagamentoRepository;
import br.com.patrick.ionic.repositories.PedidoRepository;
import br.com.patrick.ionic.repositories.ProdutoRepository;

@SpringBootApplication
public class IonicApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(IonicApplication.class, args);
	}

	@Autowired
	CategoriaRepository categoriaRepo;
	
	@Autowired
	ProdutoRepository produtoRepo;
	
	@Autowired
	EstadoRepository estadoRepo;
	
	@Autowired
	CidadeRepository cidadeRepo;
	
	@Autowired
	ClienteRepository clienteRepo;
	
	@Autowired 
	EnderecoRepository enderecoRepo;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto("Computador", 2000.00);
		Produto p2 = new Produto("Impressora", 800.00);
		Produto p3 = new Produto("Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat2);
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepo.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade("Uberlandia", est1);
		Cidade cid2 = new Cidade("São Paulo", est2);
		Cidade cid3 = new Cidade("Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente("Maria Silva", "maria@gmail.com", "09887678967", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("83987651234", "81987612345"));
		
		Endereco end1 = new Endereco("Rua Flores", "303", "Apto 301", "58034890", cid1, cli1);
		Endereco end2 = new Endereco("Rua Floreano", "302", "Apto 301", "58034790", cid2, cli1);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepo.save(cli1);
		
		enderecoRepo.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(sdf.parse("10/01/2021 10:23"), cli1, end1);
		Pedido ped2 = new Pedido(sdf.parse("11/01/2021 11:45"), cli1, end2);
		
		Pagamento pag1 = new PagamentoComCartao(EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);
		Pagamento pag2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, ped2, sdf.parse("12/01/2021 00:00"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
		
		ItemPedido item1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido item2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido item3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(item1, item2));
		ped2.getItens().add(item3);
		
		p1.getItens().add(item1);
		p2.getItens().add(item3);
		p3.getItens().add(item2);
		
		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
	}

}
