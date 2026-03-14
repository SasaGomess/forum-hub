package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.TopicResponseDTO;
import br.com.api.forum_hub.dtos.TopicUpdateDTO;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateTopicUseCase {
    private final TopicRepository topicRepository;

    public UpdateTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public TopicResponseDTO update(TopicUpdateDTO data, Long id, User userAuth){
        if (id == null) throw new ValidationException("Id inválido");

        Topic topic = topicRepository.findByIdAndAuthor(id, userAuth).orElseThrow(() -> new EntityNotFoundException("Nenhum tópico foi encontrado"));

        topic.update(data);
        topicRepository.save(topic);

        return new TopicResponseDTO(topic.getId(), topic.getTittle(), topic.getMessage(), topic.getStatus(), topic.getCourse().getName(), topic.getCreationDate());
    }
}
