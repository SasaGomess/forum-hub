package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTittleAndMessage(@NotBlank String tittle, @NotBlank String message);

    Optional<Topic> findByIdAndAuthor(Long id, User user);

    List<Topic> findAllByAuthor(User userAuth);
}
