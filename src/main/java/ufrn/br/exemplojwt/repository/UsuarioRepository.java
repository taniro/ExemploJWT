package ufrn.br.exemplojwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.br.exemplojwt.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
