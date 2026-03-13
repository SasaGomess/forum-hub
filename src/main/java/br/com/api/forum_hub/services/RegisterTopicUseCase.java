package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.TopicRegisterDTO;
import br.com.api.forum_hub.dtos.TopicResponseDTO;
import br.com.api.forum_hub.models.Course;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.models.enums.Status;
import br.com.api.forum_hub.repositories.CourseRepository;
import br.com.api.forum_hub.repositories.TopicRepository;
import br.com.api.forum_hub.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterTopicUseCase {

    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public RegisterTopicUseCase(TopicRepository topicRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public TopicResponseDTO register(TopicRegisterDTO data, User userAuth){
        if (!userRepository.existsById(userAuth.getId())) throw new ValidationException("Autor não foi encontrado");

        User user = userRepository.getReferenceById(userAuth.getId());
        System.out.println(user);
        Course course = courseRepository.findByName(data.course());

        if (course == null){
            throw new ValidationException("Curso não foi encontrado.");
        }

        if (topicRepository.existsByTittleAndMessage(data.tittle(), data.message())) throw new ValidationException("Tópico já existente com mesmo nome e menssagem!");

        var topic = new Topic(null, data.tittle(), data.message(), LocalDateTime.now(), Status.AGUARDANDO_RESPOSTA, user, course, null);

        topicRepository.save(topic);

        return new TopicResponseDTO(topic);
    }
}
