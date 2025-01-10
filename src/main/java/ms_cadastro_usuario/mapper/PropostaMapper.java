package ms_cadastro_usuario.mapper;

import java.text.NumberFormat;
import java.util.Collection;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ms_cadastro_usuario.dto.PropostaReqDTO;
import ms_cadastro_usuario.dto.PropostaRespDTO;
import ms_cadastro_usuario.entity.Proposta;

@Mapper
public interface PropostaMapper {

	PropostaMapper INSTANCE = Mappers.getMapper(PropostaMapper.class);

	@Mapping(source = "usuario.nome", target = "nome")
	@Mapping(source = "usuario.sobrenome", target = "sobrenome")
	@Mapping(source = "usuario.cpf", target = "cpf")
	@Mapping(source = "usuario.renda", target = "renda")
	@Mapping(source = "usuario.telefone", target = "telefone")
	@Mapping(expression = "java(setValorSolicitadoFmt(proposta))", target = "valorSolicitadoFmt")
	PropostaRespDTO fromEntityToDTO(Proposta proposta);

	@InheritConfiguration
	@Mapping(target = "usuario.nome", source = "nome")
	@Mapping(target = "usuario.sobrenome", source = "sobrenome")
	@Mapping(target = "usuario.cpf", source = "cpf")
	@Mapping(target = "usuario.renda", source = "renda")
	@Mapping(target = "usuario.telefone", source = "telefone")
	@Mapping(target = "integrada", constant = "true")
	@Mapping(target = "aprovado", ignore = true)
	@Mapping(target = "observacao", ignore = true)
	@Mapping(target = "id", ignore = true)
	Proposta fromReqDtoToEntity(PropostaReqDTO dto);

	Collection<PropostaRespDTO> fromListEntityToListDTO(Collection<Proposta> propostas);
	
	default String setValorSolicitadoFmt(Proposta proposta) {
		return NumberFormat.getCurrencyInstance().format(proposta.getValorSolicitado());
	}
}
