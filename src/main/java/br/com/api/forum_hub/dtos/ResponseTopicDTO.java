package br.com.api.forum_hub.dtos;

import br.com.api.forum_hub.models.Topic;
import com.fasterxml.jackson.annotation.JsonAlias;

public record ResponseTopicDTO(
        Long id,
        String tittle,
        String message,
        @JsonAlias("status")
        String status,
        @JsonAlias("nomeCourso")
        String course
) {
    public ResponseTopicDTO(Topic topic){
        this(topic.getId(),topic.getTittle(), topic.getMessage(), topic.getStatus().getStatusPortugues(), topic.getCourse().getName());
    }
}
