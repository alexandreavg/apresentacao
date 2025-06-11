package br.com.alexandre.email.messageria.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import br.com.alexandre.email.dtos.EmailRespostaDTO;
import br.com.alexandre.email.services.implementations.EmailServiceImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumidorExcecoes {
	
	private final EmailServiceImpl servico;
	
	@RabbitListener(queues = {"email-alexandre-resposta-excecao-fila"})
	public void receive(@Payload Message<?> message) {
		EmailRespostaDTO dto = new EmailRespostaDTO((String) message.getPayload().toString());
		servico.salvarLogExcecao(dto.getId(), dto.getRetorno());
    }
	
}
