package br.com.api.forum_hub.services;

import br.com.api.forum_hub.dtos.UserRegisterDTO;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegister {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserRegister(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterDTO data){
        if (repository.existsByEmail(data.email())) throw new ValidationException("Usuário já existente com o e-mail cadastrado");

        String encoded = passwordEncoder.encode(data.password());

        User user = new User(data, encoded);

        repository.save(user);
    }
}
