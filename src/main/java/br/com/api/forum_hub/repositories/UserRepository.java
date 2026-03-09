package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
