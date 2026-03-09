package br.com.api.forum_hub.infra.security;

import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    private final UserRepository repository;

    public SecurityFilter(TokenService tokenService, UserRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    // Filtro de autorização
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJwt = recuperarToken(request);

        if (!tokenJwt.isBlank()){
            //Pega o email do usuário
            String email = tokenService.getSubject(tokenJwt);
            Optional<User> optionalUser = repository.findByEmail(email);

            if (optionalUser.isPresent()){
                var user = optionalUser.get();
                var userToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(userToken);
            }

        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        var authorization = request.getHeader("Authorization");

        if (!authorization.isBlank()){
           return authorization.replace("Berear ", "");
        }
        return "";
    }
}
