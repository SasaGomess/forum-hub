package br.com.api.forum_hub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResponseRequestDTO (
        @NotBlank
        String message,
        @NotBlank
        @Size(max = 300)
        String solution,
        @NotNull
        Long topic_id
) {
}
