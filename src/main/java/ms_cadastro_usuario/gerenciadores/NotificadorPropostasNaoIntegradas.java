package ms_cadastro_usuario.gerenciadores;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ms_cadastro_usuario.config.RabbitMqConfig;
import ms_cadastro_usuario.entity.Proposta;
import ms_cadastro_usuario.repositories.PropostaRepository;
import ms_cadastro_usuario.services.NotificacaoRabbitMQService;

@Service
public class NotificadorPropostasNaoIntegradas {

	private final PropostaRepository propostaRepository;

	private final NotificacaoRabbitMQService notificacaoRabbitMQService;

	private final Logger logger = LoggerFactory.getLogger(NotificadorPropostasNaoIntegradas.class);

	public NotificadorPropostasNaoIntegradas(PropostaRepository propostaRepository,
			NotificacaoRabbitMQService notificacaoRabbitMQService) {
		this.propostaRepository = propostaRepository;
		this.notificacaoRabbitMQService = notificacaoRabbitMQService;
	}

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	public void notificarPropostasSemIntegracao() {
		this.propostaRepository.findByIntegrada(false).forEach(p -> {
			try {
				this.notificacaoRabbitMQService.notificar(p, RabbitMqConfig.EXCHANGE_PROPOSTA_PENDENTE);
				atualizarPropostaParaIntegrada(p);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				e.printStackTrace();
			}
		});
	}

	private void atualizarPropostaParaIntegrada(Proposta p) {
		p.setIntegrada(true);
		this.propostaRepository.save(p);
	}

}
