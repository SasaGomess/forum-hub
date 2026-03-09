package br.com.api.forum_hub.dtos;

import br.com.api.forum_hub.models.Topic;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;

public record ResponseTopicDTO(
        Long id,
        String tittle,
        String message,
        String status,
        String course,
        LocalDateTime creationDate
) {
    public ResponseTopicDTO(Topic topic){
        this(topic.getId(),topic.getTittle(), topic.getMessage(), topic.getStatus().getStatusPortugues(), topic.getCourse().getName(), topic.getCreationDate());
    }
}
