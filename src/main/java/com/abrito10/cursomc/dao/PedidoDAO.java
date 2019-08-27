package com.abrito10.cursomc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abrito10.cursomc.domain.Pedido;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Integer> {

}
