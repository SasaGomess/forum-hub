package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.models.Response;
import br.com.api.forum_hub.models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findAllByAuthor(User user);

    Optional<Response> findByIdAndAuthor(Long id, User user);

    boolean existsByMessage(String message);
}
