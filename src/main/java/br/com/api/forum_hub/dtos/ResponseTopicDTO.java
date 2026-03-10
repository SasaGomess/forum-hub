package br.com.api.forum_hub.dtos;

import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;

public record ResponseTopicDTO(
        Long id,
        String tittle,
        String message,
        Status status,
        String course,
        LocalDateTime creationDate
) {
    public ResponseTopicDTO(Topic topic){
        this(topic.getId(),topic.getTittle(), topic.getMessage(), topic.getStatus(), topic.getCourse().getName(), topic.getCreationDate());
    }
}
