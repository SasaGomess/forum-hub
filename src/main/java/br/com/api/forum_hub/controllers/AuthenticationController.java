package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.UserRegisterDTO;
import br.com.api.forum_hub.infra.security.ResponseDataJWT;
import br.com.api.forum_hub.services.UserLoginService;
import br.com.api.forum_hub.services.UserRegister;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final UserRegister registerService;
    private final UserLoginService loginService;

    public AuthenticationController(UserRegister registerService, UserLoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Void> register(@RequestBody UserRegisterDTO registerData){
        registerService.register(registerData);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<ResponseDataJWT> login(@RequestBody UserRegisterDTO registerData) {
        ResponseDataJWT responseDataJWT = loginService.login(registerData);
        return ResponseEntity.ok(responseDataJWT);
    }

}
