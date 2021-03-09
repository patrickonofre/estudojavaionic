package br.com.patrick.ionic.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.patrick.ionic.domain.Categoria;
import br.com.patrick.ionic.dto.CategoriaDTO;
import br.com.patrick.ionic.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaServ;
	
	@GetMapping
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		return ResponseEntity.ok(categoriaServ.find(id));
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<CategoriaDTO> listDTO = categoriaServ.findAll()
				.stream().map(cat -> new CategoriaDTO(cat))
				.collect(Collectors.toList());
		return ResponseEntity.ok(listDTO);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "page", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "page", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "page", defaultValue = "ASC") String direction) {
		
		Page<Categoria> list = this.categoriaServ.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(cat -> new CategoriaDTO(cat));
		
		return ResponseEntity.ok(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = this.categoriaServ.fromDTO(categoriaDTO);
		categoria = categoriaServ.insert(categoria); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
		Categoria categoria = categoriaServ.fromDTO(categoriaDTO);
		categoria.setId(id);
		categoria = categoriaServ.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoriaServ.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
