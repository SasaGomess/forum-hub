package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.dtos.UserRegisterDTO;
import br.com.api.forum_hub.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

   boolean existsByEmail(String email);
}
