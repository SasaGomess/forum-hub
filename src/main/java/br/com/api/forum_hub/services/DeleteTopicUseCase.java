package br.com.api.forum_hub.services;

import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteTopicUseCase {
    private TopicRepository repository;

    public DeleteTopicUseCase(TopicRepository repository) {
        this.repository = repository;
    }

    public void delete(Long id, User userAuth){
        if (id == null) throw new ValidationException("Id inválido");

        Topic topic = repository.findByIdAndAuthor(id, userAuth).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        repository.delete(topic);
    }
}
