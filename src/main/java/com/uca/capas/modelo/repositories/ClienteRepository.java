package com.uca.capas.modelo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uca.capas.modelo.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
