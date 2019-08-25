package com.abrito10.cursomc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abrito10.cursomc.domain.Cliente;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

}
