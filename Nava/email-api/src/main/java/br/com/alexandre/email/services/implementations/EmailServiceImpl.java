package br.com.alexandre.email.services.implementations;

import java.util.Date;

import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.alexandre.email.dtos.EmailDTO;
import br.com.alexandre.email.entities.Email;
import br.com.alexandre.email.messageria.producers.ProdutorEnviar;
import br.com.alexandre.email.repositories.EmailRepository;
import br.com.alexandre.email.services.EmailServico;
import br.com.alexandre.email.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailServico {
	
	private final ProdutorEnviar producer;
	private final EmailRepository repository;
	
	@Override
	public EmailDTO enviar(EmailDTO dto) throws Exception {
		Email entity = new Email();
		
		dto.setEnviado(false);
		
		//Salva o email no banco de dados.
		repository.save(new Email(dto, "0 - Aguardando servidor de envio", new Date()));
		dto.setId(entity.getId());
		dto.setRetorno("Enviado para servidor de fila.");
		log.info("Enviado para servidor de fila: " + dto.getDtoParaLogAsString());
		
		try {
			producer.enviar(dto);
		} catch (JsonProcessingException | AmqpException e) {
			log.error("E-mail não enviado: " + dto.getDtoParaLogAsString() + " - Descrição: " + e.getMessage());
			
			salvarLogExcecao(entity.getId(), e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return new EmailDTO(entity);
	}

	@Override
	public void salvarLogSucesso(Long id, String retorno) {
		Email entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Email nao encontrado para id: " + id));
		entity.setRetorno(retorno);
		entity.setEnviado(true);
		
		repository.save(entity);
	}

	@Override
	public void salvarLogExcecao(Long id, String retorno) {
		Email entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Email nao encontrado para id: " + id));
		entity.setRetorno(retorno);
		entity.setEnviado(false);
		
		repository.save(entity);
	}

	@Override
	public EmailDTO buscarPorId(Long id) {
		Email entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Email nao encontrado para id: " + id));
		return new EmailDTO(entity);
	}

}
