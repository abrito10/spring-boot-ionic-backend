package com.abrito10.cursomc.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abrito10.cursomc.domain.Cliente;
import com.abrito10.cursomc.domain.Pedido;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Integer> {
	
	@Transactional(readOnly=true)
	//Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

	Page<Pedido> findByCliente(Cliente cliente, PageRequest pageRequest);

}
