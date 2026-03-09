package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.models.Topic;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTittleAndMessage(@NotBlank String tittle, @NotBlank String message);
}
