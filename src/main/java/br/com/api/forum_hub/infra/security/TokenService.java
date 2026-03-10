package br.com.api.forum_hub.infra.security;

import br.com.api.forum_hub.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;


@Service
public class TokenService {

    @Value(value = "${spring.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algoritm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withSubject(user.getEmail())
                    .withIssuer("apiforumhub")
                    .withExpiresAt(expiration())
                    .sign(algoritm);
        } catch (
                JWTCreationException e) {
            throw new RuntimeException("Erro ao criar token jwt", e);
        }

    }

    public String getSubject(String token) {
        try {
            var algoritm = Algorithm.HMAC256(secret);
            System.out.println(token);
            return JWT.require(algoritm)
                    .withIssuer("apiforumhub")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao recuperar o usuário pelo token jwt. Token expirado ou inválido!", e);
        }
    }

    private Instant expiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}