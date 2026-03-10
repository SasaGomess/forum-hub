package br.com.api.forum_hub.infra.security;

public record ResponseDataJWT(
        String token,
        String tipo
) {
}
