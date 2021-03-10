package com.example.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.carros.domain.dto.CarroDTO;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
	public List<CarroDTO> getCarros() {
		
		List<Carro> carros = rep.findAll();
		
		List<CarroDTO> list = carros.stream().map(CarroDTO::create).collect(Collectors.toList());
		
		return list;
	}
	
	public Optional<CarroDTO> getCarrosById(Long id) {
		return rep.findById(id).map(CarroDTO::create);
	}
	
	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return rep.getCarrosByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possivel inserir o registro");
		return CarroDTO.create(rep.save(carro));
	}

	public Carro update(Carro carro, long id) {
		
		Assert.notNull(id, "Não foi possivel atualizar o registro");
		
		//busca o carro no banco de dados
		Optional<Carro> optional = rep.findById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			//copiar as propiedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id "+ db.getId());
			
			//Atualiza o carro
			rep.save(db);
			
			return db;
			
		} else {
			throw  new RuntimeException("não foi possivel atualizar o registro");
		}
		
	}

	public void deleteCar(long id) {
		rep.deleteById(id);
	}

}
