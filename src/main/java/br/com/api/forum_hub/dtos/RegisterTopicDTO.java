package br.com.api.forum_hub.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterTopicDTO(
        @NotBlank
        @JsonAlias(value = "titulo")
        String tittle,
        @NotBlank
        @JsonAlias(value = "mensagem")
        String message,
        @NotNull
        @JsonAlias(value = "autorId")
        Long authorId,
        @NotBlank
        @JsonAlias(value = "nomeCurso")
        String course
) {
}
