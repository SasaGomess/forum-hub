package br.com.api.forum_hub.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicRegisterDTO(
        @NotBlank
        @JsonAlias(value = "titulo")
        String tittle,
        @NotBlank
        @JsonAlias(value = "mensagem")
        String message,
        @NotNull
        @NotBlank
        @JsonAlias(value = "nomeCurso")
        String course
) {
}
