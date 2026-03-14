package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.ResponseDTO;
import br.com.api.forum_hub.dtos.ResponseRequestDTO;

import br.com.api.forum_hub.dtos.ResponseUpdateDTO;
import br.com.api.forum_hub.models.Response;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.ResponseRepository;
import br.com.api.forum_hub.services.CreateResponseUseCase;
import br.com.api.forum_hub.services.UpdateResponseUseCase;
import br.com.api.forum_hub.services.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/responses")
public class ResponseController {

    private final CreateResponseUseCase responseCreateUseCase;
    private final ResponseRepository responseRepository;
    private final UpdateResponseUseCase responseUpdateUseCase;

    public ResponseController(CreateResponseUseCase responseCreateUseCase, ResponseRepository responseRepository, UpdateResponseUseCase responseUpdateUseCase) {
        this.responseCreateUseCase = responseCreateUseCase;
        this.responseRepository = responseRepository;
        this.responseUpdateUseCase = responseUpdateUseCase;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid ResponseRequestDTO data, @AuthenticationPrincipal User user, UriComponentsBuilder uriBuilder){
        ResponseDTO responseDTO = responseCreateUseCase.create(data, user);

        URI uri = uriBuilder
                .path("/responses/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<ResponseDTO>> listAll(@AuthenticationPrincipal User user){
        List<Response> allResponses = responseRepository.findAllByAuthor(user);

        List<ResponseDTO> responseDtos = allResponses.stream().map(ResponseDTO::new).toList();

        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid ResponseUpdateDTO data, @AuthenticationPrincipal User user, @PathVariable Long id){
        ResponseDTO updatedResponse = responseUpdateUseCase.update(data, user, id);
        return ResponseEntity.ok(updatedResponse);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable Long id){
        if (id == null) throw new ValidationException("O id da resposta é inválido");
        Response response = responseRepository.findByIdAndAuthor(id, user).orElseThrow(() -> new EntityNotFoundException("Nenhuma resposta foi encontrada"));
        responseRepository.deleteById(response.getId());
        return ResponseEntity.noContent().build();
    }
}
