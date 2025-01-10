package ms_cadastro_usuario.controllers;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ms_cadastro_usuario.dto.PropostaReqDTO;
import ms_cadastro_usuario.dto.PropostaRespDTO;
import ms_cadastro_usuario.services.PropostaService;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

	private final PropostaService propostaService;

	public PropostaController(PropostaService propostaService) {
		this.propostaService = propostaService;
	}

	@PostMapping
	public ResponseEntity<PropostaRespDTO> salvarProposta(@RequestBody PropostaReqDTO proposta) {
		PropostaRespDTO propostaSalva = this.propostaService.criar(proposta);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(propostaSalva.id()).toUri()).body(propostaSalva);
	}

	@GetMapping
	public ResponseEntity<Collection<PropostaRespDTO>> buscarPropostas() {
		return ResponseEntity
				.ok(propostaService.buscarPropostas());
	}
}
