package br.com.api.forum_hub.dtos;

import br.com.api.forum_hub.models.Response;

import java.time.LocalDateTime;

public record ResponseDTO(
                                    Long id,
                                  String message,
                                  String solution,
                                  LocalDateTime creationDate,
                                  Long topic_id,
                                  Long user_id){
    public ResponseDTO(Response response){
        this(response.getId(), response.getMessage(), response.getSolution(), response.getCreationDate(), response.getTopic().getId(), response.getAuthor().getId());
    }
}
