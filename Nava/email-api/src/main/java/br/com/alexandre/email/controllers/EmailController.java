package br.com.alexandre.email.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alexandre.email.dtos.EmailDTO;
import br.com.alexandre.email.services.implementations.EmailServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
	
	private final EmailServiceImpl service;
	
	@PostMapping(value = "/enviar")
	public ResponseEntity<EmailDTO> enviar(@RequestBody EmailDTO dto) throws Exception {
		dto = service.enviar(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping(value = "buscarPorId/{id}")
	public ResponseEntity<EmailDTO> buscarPorId(@PathVariable Long id) {
		EmailDTO dto = service.buscarPorId(id);
		
		return ResponseEntity.ok().body(dto);
	}

}
