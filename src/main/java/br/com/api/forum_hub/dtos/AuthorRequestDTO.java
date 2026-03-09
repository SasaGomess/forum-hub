package br.com.api.forum_hub.dtos;

public record AuthorRequestDTO(
        Long id,
        String name,
        String email
) {
}
