package br.com.patrick.ionic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.patrick.ionic.domain.Categoria;
import br.com.patrick.ionic.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaServ;
	
	@GetMapping
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		return ResponseEntity.ok(categoriaServ.buscar(id));
	}
}
