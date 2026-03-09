package br.com.api.forum_hub.infra.security;

import br.com.api.forum_hub.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter implements OncePerRequestFilter {
    private final TokenService service;

    private final UserRepository repository;

    public SecurityFilter(TokenService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        var tokenJwt = recuperarToken(request);
//    }

//    private String recuperarToken(HttpServletRequest request){
//        var authorization = request.getHeader("Authorization");
//
//        var token =
//    }
}
