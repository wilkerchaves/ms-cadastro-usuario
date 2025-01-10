package ms_cadastro_usuario.dto;

public record PropostaReqDTO(
		String nome,
		String sobrenome,
		String telefone,
		String cpf,
		Double renda,
		Double valorSolicitado,
		int prazo
		) {

}
