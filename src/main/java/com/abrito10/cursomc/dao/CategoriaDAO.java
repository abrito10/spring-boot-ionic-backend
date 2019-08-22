package com.abrito10.cursomc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abrito10.cursomc.domain.Categoria;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Integer> {

}
