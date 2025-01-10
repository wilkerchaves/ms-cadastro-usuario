package ms_cadastro_usuario.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ms_cadastro_usuario.config.RabbitMqConfig;
import ms_cadastro_usuario.dto.PropostaReqDTO;
import ms_cadastro_usuario.dto.PropostaRespDTO;
import ms_cadastro_usuario.entity.Proposta;
import ms_cadastro_usuario.mapper.PropostaMapper;
import ms_cadastro_usuario.repositories.PropostaRepository;

@Service
public class PropostaService {

	private final PropostaRepository propostaRepository;
	private final PropostaMapper mapper = PropostaMapper.INSTANCE;
	private final NotificacaoRabbitMQService notificacaoService;

	public PropostaService(PropostaRepository propostaRepository, NotificacaoRabbitMQService notificacaoService) {
		this.propostaRepository = propostaRepository;
		this.notificacaoService = notificacaoService;
	}

	public PropostaRespDTO criar(PropostaReqDTO proposta) {
		Proposta propostaEntity = this.mapper.fromReqDtoToEntity(proposta);
		Proposta propostaSalva = this.propostaRepository.save(propostaEntity);
		this.notificaRabbitMQ(propostaSalva);
		return this.mapper.fromEntityToDTO(propostaSalva);
	}

	private void notificaRabbitMQ(Proposta proposta) {
		try {
			this.notificacaoService.notificar(proposta, RabbitMqConfig.EXCHANGE_PROPOSTA_PENDENTE);
		} catch (RuntimeException e) {
			proposta.setIntegrada(false);
			this.propostaRepository.save(proposta);
		}
	}

	public List<PropostaRespDTO> buscarPropostas() {
		List<PropostaRespDTO> listaPropostas = (List<PropostaRespDTO>) mapper
				.fromListEntityToListDTO(this.propostaRepository.findAll());
		return listaPropostas;
	}

}
