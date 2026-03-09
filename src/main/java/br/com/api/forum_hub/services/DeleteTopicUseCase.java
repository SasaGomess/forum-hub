package br.com.api.forum_hub.services;

import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.repositories.TopicRepository;
import jakarta.persistence.EntityNotFoundException;

public class DeleteTopicUseCase {
    private TopicRepository repository;

    public DeleteTopicUseCase(TopicRepository repository) {
        this.repository = repository;
    }

    public void delete(Long id){
        if (id == null) throw new ValidationException("Id inválido");

        Topic topic = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        repository.delete(topic);
    }
}
