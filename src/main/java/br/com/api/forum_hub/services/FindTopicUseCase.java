package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.ResponseTopicDTO;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.repositories.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindTopicUseCase {
    private TopicRepository topicRepository;

    public FindTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public ResponseTopicDTO find(Long id){
        if (id == null) throw new ValidationException("Id inválido");

        Topic topic = topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        return new ResponseTopicDTO(topic);
    }
}
