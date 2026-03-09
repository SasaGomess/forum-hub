package br.com.api.forum_hub.infra.security;

import br.com.api.forum_hub.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

    public String generateToken (User user){
        var algoritm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("api-forum-hub")
                .withSubject(user.getName())
                .withExpiresAt(expiration())
                .sign(algoritm);

    }

    public String getSubject(String token){
        var algoritm = Algorithm.HMAC256(secret);
        return JWT.require(algoritm)
                .withIssuer("api-forum-hub")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant expiration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
    }
}