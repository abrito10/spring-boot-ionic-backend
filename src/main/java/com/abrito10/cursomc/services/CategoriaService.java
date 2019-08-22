package com.abrito10.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abrito10.cursomc.dao.CategoriaDAO;
import com.abrito10.cursomc.domain.Categoria;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaDAO repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id); 
		return obj.orElse(null);
	}
}
