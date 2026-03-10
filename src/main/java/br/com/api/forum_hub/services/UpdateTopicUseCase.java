package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.ResponseTopicDTO;
import br.com.api.forum_hub.dtos.UpdateTopicDTO;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.CourseRepository;
import br.com.api.forum_hub.repositories.TopicRepository;
import br.com.api.forum_hub.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateTopicUseCase {
    private TopicRepository topicRepository;

    public UpdateTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public ResponseTopicDTO update(UpdateTopicDTO data, Long id, User userAuth){
        if (id == null) throw new ValidationException("Id inválido");

        Topic topic = topicRepository.findByIdAndAuthor(id, userAuth).orElseThrow(() -> new EntityNotFoundException("Nenhum tópico foi encontrado"));

        topic.update(data);
        topicRepository.save(topic);

        return new ResponseTopicDTO(topic.getId(), topic.getTittle(), topic.getMessage(), topic.getStatus(), topic.getCourse().getName(), topic.getCreationDate());
    }
}
