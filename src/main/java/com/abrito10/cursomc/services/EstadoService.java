package com.abrito10.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abrito10.cursomc.dao.EstadoDAO;
import com.abrito10.cursomc.domain.Estado;

@Service
public class EstadoService {

	@Autowired
	private EstadoDAO repo;

	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}
}