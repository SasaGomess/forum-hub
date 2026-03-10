package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.UserRegisterDTO;
import br.com.api.forum_hub.infra.security.ResponseDataJWT;
import br.com.api.forum_hub.infra.security.TokenService;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public UserLoginService( AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    public ResponseDataJWT login(UserRegisterDTO registerData) {
       var userAuthentication = new UsernamePasswordAuthenticationToken(registerData.password(), null);
       var authenticationToken = manager.authenticate(userAuthentication);

       var token = tokenService.generateToken((User) authenticationToken.getPrincipal());

       return new ResponseDataJWT(token, "Berear");
    }
}
