package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
