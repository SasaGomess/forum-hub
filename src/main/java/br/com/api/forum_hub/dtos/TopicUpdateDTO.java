package br.com.api.forum_hub.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record TopicUpdateDTO(@JsonAlias(value = "titulo")
                             String tittle,
                             @JsonAlias(value = "mensagem")
                             String message) {
}
