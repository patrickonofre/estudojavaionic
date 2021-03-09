package br.com.patrick.ionic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.patrick.ionic.domain.Categoria;
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
	
	public Categoria insert(Categoria categoria) {
		return categoriaRepo.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		this.find(categoria.getId());
		return categoriaRepo.save(categoria);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			categoriaRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		
	}
}
