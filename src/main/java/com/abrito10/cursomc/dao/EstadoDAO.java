package com.abrito10.cursomc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abrito10.cursomc.domain.Estado;

@Repository
public interface EstadoDAO extends JpaRepository<Estado, Integer> {

}
