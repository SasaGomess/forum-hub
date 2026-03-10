package br.com.api.forum_hub.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record UpdateTopicDTO(@NotBlank
                             @JsonAlias(value = "titulo")
                             String tittle,
                             @NotBlank
                             @JsonAlias(value = "mensagem")
                             String message) {
}
