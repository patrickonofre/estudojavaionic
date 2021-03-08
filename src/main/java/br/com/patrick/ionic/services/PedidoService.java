package br.com.patrick.ionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.patrick.ionic.domain.Pedido;
import br.com.patrick.ionic.repositories.PedidoRepository;
import br.com.patrick.ionic.services.exception.ObjectNotFoundExeception;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundExeception(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
