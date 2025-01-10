package ms_cadastro_usuario.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import ms_cadastro_usuario.entity.Proposta;

@Service
public class NotificacaoRabbitMQService {

	private RabbitTemplate rabbitTemplate;

	public NotificacaoRabbitMQService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void notificar(Proposta proposta, String exchange) {
		this.rabbitTemplate.convertAndSend(exchange, "", proposta);

	}

}
