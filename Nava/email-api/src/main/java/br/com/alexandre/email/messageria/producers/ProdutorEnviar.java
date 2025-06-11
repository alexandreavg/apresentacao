package br.com.alexandre.email.messageria.producers;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alexandre.email.dtos.EmailDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProdutorEnviar {
	
	private final AmqpTemplate amqpTemplate;
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public void enviar(EmailDTO dto) throws JsonProcessingException, AmqpException {
		amqpTemplate.convertAndSend(
				"email-alexandre-enviar-exchange",
		        "email-alexandre-enviar-route-key",
		        objectMapper.writeValueAsString(dto)
			);
	}

}
