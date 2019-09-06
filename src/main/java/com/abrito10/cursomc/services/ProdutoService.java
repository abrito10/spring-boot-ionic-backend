package com.abrito10.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.abrito10.cursomc.dao.CategoriaDAO;
import com.abrito10.cursomc.dao.ProdutoDAO;
import com.abrito10.cursomc.domain.Categoria;
import com.abrito10.cursomc.domain.Produto;
import com.abrito10.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoDAO repo;
	
	@Autowired 
	private CategoriaDAO categoriaDao;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	public Page<Produto> search(String nome, List<Integer> ids,Integer page,Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaDao.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
}