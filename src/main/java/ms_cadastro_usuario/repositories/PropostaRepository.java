package ms_cadastro_usuario.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ms_cadastro_usuario.entity.Proposta;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
	
	@Query
	List<Proposta> findByIntegrada(boolean integrada);

}
