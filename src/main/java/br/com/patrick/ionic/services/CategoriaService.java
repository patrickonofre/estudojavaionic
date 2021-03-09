package br.com.patrick.ionic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.patrick.ionic.domain.Categoria;
import br.com.patrick.ionic.dto.CategoriaDTO;
import br.com.patrick.ionic.repositories.CategoriaRepository;
import br.com.patrick.ionic.services.exception.DataIntegrityException;
import br.com.patrick.ionic.services.exception.ObjectNotFoundExeception;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Categoria find(Integer id) {
		return categoriaRepo.findById(id).orElseThrow(() -> new ObjectNotFoundExeception(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> findAll() {
		return categoriaRepo.findAll();
	}
	
	public Categoria insert(Categoria categoria) {
		return categoriaRepo.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		Categoria newCategoria = this.find(categoria.getId());
		this.updateData(newCategoria, categoria);
		return categoriaRepo.save(newCategoria);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			categoriaRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}	
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	private void updateData(Categoria newCategoria, Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}
}
