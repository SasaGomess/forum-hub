package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.TopicResponseDTO;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindTopicUseCase {
    private final TopicRepository topicRepository;

    public FindTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicResponseDTO find(Long id, User user){
        if (id == null) throw new ValidationException("Id inválido");

        Topic topic = topicRepository.findByIdAndAuthor(id, user).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));;

        return new TopicResponseDTO(topic);
    }
}
