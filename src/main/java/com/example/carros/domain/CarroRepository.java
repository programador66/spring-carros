package com.example.carros.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CarroRepository extends JpaRepository<Carro, Long>{

	List<Carro> getCarrosByTipo(String tipo);

}
