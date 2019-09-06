package com.abrito10.cursomc.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abrito10.cursomc.domain.Categoria;
import com.abrito10.cursomc.domain.Produto;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Integer> {
	
	@Transactional(readOnly = true)
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat in :categorias")
	//Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias,Pageable pageRequest);
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias,Pageable pageRequest);

}
