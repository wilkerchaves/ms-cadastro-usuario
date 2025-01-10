package ms_cadastro_usuario.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMqConfig {

	private static final String FILA_PROPOSTA_PENDENTE_MS_ANALISE_CREDITO = "proposta-pendente.ms-analise-credito";
	private static final String FILA_PROPOSTA_PENDENTE_MS_NOTIFICACAO = "proposta-pendente.ms-notificacao";
	private static final String FILA_PROPOSTA_CONCLUIDA_MS_PROPOSTA = "proposta-concluida.ms-proposta";
	private static final String FILA_PROPOSTA_CONCLUIDA_MS_NOTIFICACAO = "proposta-concluida.ms-notificacao";
	public static final String EXCHANGE_PROPOSTA_PENDENTE = "proposta-pendente.ex";

	@Bean
	Queue criaFilaAnaliseCreditoPendente() {
		return QueueBuilder.durable(FILA_PROPOSTA_PENDENTE_MS_ANALISE_CREDITO).build();
	}

	@Bean
	Queue criaFilaNotificacaoPendente() {
		return QueueBuilder.durable(FILA_PROPOSTA_PENDENTE_MS_NOTIFICACAO).build();
	}

	@Bean
	Queue criaFilaAnaliseCreditoConcluida() {
		return QueueBuilder.durable(FILA_PROPOSTA_CONCLUIDA_MS_PROPOSTA).build();
	}

	@Bean
	Queue criaFilaNotificacaoConcluida() {
		return QueueBuilder.durable(FILA_PROPOSTA_CONCLUIDA_MS_NOTIFICACAO).build();
	}

	@Bean
	RabbitAdmin criarRabbitAdmin(ConnectionFactory factory) {
		return new RabbitAdmin(factory);
	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin admin) {
		System.out.println("inicializaAdmin");
		return event -> admin.initialize();
	}

	@Bean
	FanoutExchange criarExchangePropostaPendente() {
		return ExchangeBuilder.fanoutExchange(EXCHANGE_PROPOSTA_PENDENTE).build();
	}

	@Bean
	Binding criaBindingAnalisePendentePropostaPendente() {
		return BindingBuilder.bind(criaFilaAnaliseCreditoPendente()).to(criarExchangePropostaPendente());
	}

	@Bean
	Binding criaBindingNotificacaoPendentePropostaPendente() {
		return BindingBuilder.bind(criaFilaNotificacaoPendente()).to(criarExchangePropostaPendente());
	}

	@Bean
	MessageConverter getJackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter(new ObjectMapper());
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
		rabbitTemplate.setMessageConverter(getJackson2JsonMessageConverter());
		return rabbitTemplate;
	}
}
