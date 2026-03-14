package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.ResponseDTO;
import br.com.api.forum_hub.dtos.ResponseRequestDTO;
import br.com.api.forum_hub.models.Response;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.ResponseRepository;
import br.com.api.forum_hub.repositories.TopicRepository;
import br.com.api.forum_hub.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateResponseUseCase {
    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public CreateResponseUseCase(ResponseRepository responseRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.responseRepository = responseRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public ResponseDTO create(ResponseRequestDTO data, User userData){
        if (!topicRepository.existsById(data.topic_id())) throw new EntityNotFoundException("Tópico não encontrado com o id enviado");
        if (responseRepository.existsByMessage(data.message())) throw new ValidationException("Já existe uma resposta com a mensagem enviada");

        User user = userRepository.getReferenceById(userData.getId());
        Topic topic = topicRepository.getReferenceById(data.topic_id());

        Response response = new Response(null, data.message(), LocalDateTime.now(), data.solution(), user, topic);

        Response savedResponse = responseRepository.save(response);

        return new ResponseDTO(savedResponse);
    }
}
