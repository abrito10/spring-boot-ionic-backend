package com.abrito10.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abrito10.cursomc.dao.CidadeDAO;
import com.abrito10.cursomc.domain.Cidade;

@Service
public class CidadeService {

	@Autowired
	private CidadeDAO repo;

	public List<Cidade> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}
}