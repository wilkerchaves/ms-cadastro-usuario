package ms_cadastro_usuario.dto;

public record PropostaRespDTO(
		Long id,
		String nome,
		String sobrenome,
		String telefone,
		String cpf,
		Double renda,
		String valorSolicitadoFmt,
		int prazo,
		Boolean aprovado,
		String observacao
		) {

}
