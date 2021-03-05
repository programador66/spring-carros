package com.example.carros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;

@SpringBootTest
class CarrosApplicationTests {

	@Autowired CarroService service;
	
	@Test
	public void test1() {
		Carro carro = new Carro();
		
		carro.setNome("ferrari");
		carro.setTipo("esportivos");
		
		CarroDTO c =  service.insert(carro);
		
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		//buscar o objeto
		Optional<CarroDTO> op = service.getCarrosById(id);
		
		assertTrue(op.isPresent());
		
		c = op.get();
		
		assertEquals("ferrari",c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		//Deletar o objeto
		service.deleteCar(id);
		
		// Verificar se deletou
		assertFalse(service.getCarrosById(id).isPresent());
		
	}

}
