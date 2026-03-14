package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.ResponseDTO;
import br.com.api.forum_hub.dtos.ResponseUpdateDTO;
import br.com.api.forum_hub.models.Response;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.CourseRepository;
import br.com.api.forum_hub.repositories.ResponseRepository;
import br.com.api.forum_hub.repositories.TopicRepository;
import br.com.api.forum_hub.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateResponseUseCase {
    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;


    public UpdateResponseUseCase(ResponseRepository responseRepository, TopicRepository topicRepository) {
        this.responseRepository = responseRepository;
        this.topicRepository = topicRepository;
    }

    public ResponseDTO update(ResponseUpdateDTO data, User user, Long id){
        if (id == null) throw new ValidationException("O id é inválido");
        if (!topicRepository.existsById(data.topic_id())) throw new EntityNotFoundException("Tópico não encontrado com o id enviado");

       Response response = responseRepository.findByIdAndAuthor(id, user).orElseThrow(() -> new EntityNotFoundException("A resposta não foi encontrada com o id enviado"));
       response.atualizar(data);

       responseRepository.save(response);
       return new ResponseDTO(response);
    }
}
