package ms_cadastro_usuario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ms_cadastro_usuario.entity.Proposta;
import ms_cadastro_usuario.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
