package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarrosAPITest {

	@Autowired CarroService service;
	protected TestRestTemplate rest;

	private ResponseEntity<CarroDTO> getCarro(String url) {
		return rest.getForEntity(url, CarroDTO.class);
	}

	private ResponseEntity<List<CarroDTO>> getCarros(String url) {
		return rest.exchange(
				url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CarroDTO>>(){}
		);
	}

	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Porshe");
		carro.setTipo("esportivos");

		//Insert
		ResponseEntity response = rest.postForEntity("/api/v1/carros",carro,null);
		System.out.println(response);

		//verifica se criou
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		//Busca Objeto
		String location = response.getHeaders().get("location").get(0);;
		CarroDTO c = getCarro(location).getBody();

		assertNotNull(c);
		assertEquals("Porshe",c.getNome());
		assertEquals("esportivos", c.getTipo());

		// Deletar o objeto
		rest.delete(location);

		//verificar se deletou
		assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
	}

	@Test
	public void testGetOk() {
		ResponseEntity<CarroDTO> response = getCarro("/api/v1/carros/11");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		CarroDTO c = response.getBody();
		assertEquals("Ferrari FF", c, c.getNome());
	}
}
