package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.RegisterTopicDTO;
import br.com.api.forum_hub.dtos.ResponseTopicDTO;
import br.com.api.forum_hub.dtos.UpdateTopicDTO;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.models.User;
import br.com.api.forum_hub.repositories.TopicRepository;
import br.com.api.forum_hub.services.DeleteTopicUseCase;
import br.com.api.forum_hub.services.FindTopicUseCase;
import br.com.api.forum_hub.services.RegisterTopicUseCase;
import br.com.api.forum_hub.services.UpdateTopicUseCase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/topicos")
@RestController
public class TopicController {
    private RegisterTopicUseCase registerTopicUseCase;
    private TopicRepository repository;
    private FindTopicUseCase findTopicUseCase;
    private UpdateTopicUseCase updateTopicUseCase;
    private DeleteTopicUseCase deleteTopicUseCase;

    public TopicController(RegisterTopicUseCase registerTopicUseCase, TopicRepository repository, FindTopicUseCase findTopicUseCase, UpdateTopicUseCase updateTopicUseCase, DeleteTopicUseCase deleteTopicUseCase) {
        this.registerTopicUseCase = registerTopicUseCase;
        this.repository = repository;
        this.findTopicUseCase = findTopicUseCase;
        this.updateTopicUseCase = updateTopicUseCase;
        this.deleteTopicUseCase = deleteTopicUseCase;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseTopicDTO> register(@RequestBody @Valid RegisterTopicDTO data, UriComponentsBuilder uriBuilder, @AuthenticationPrincipal User user){
        ResponseTopicDTO responseTopic = registerTopicUseCase.register(data, user);

        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(responseTopic.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseTopic);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTopicDTO>> listAll(@AuthenticationPrincipal User userAuth){
        List<Topic> allTopics = repository.findAllByAuthor(userAuth);

        List<ResponseTopicDTO> response = allTopics.stream().map(ResponseTopicDTO::new).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTopicDTO> findTopic(@PathVariable Long id, @AuthenticationPrincipal User userAuth){
        return ResponseEntity.ok(findTopicUseCase.find(id, userAuth));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTopicDTO> update(@PathVariable Long id, @RequestBody UpdateTopicDTO updateTopicDTO, @AuthenticationPrincipal User userAuth){
        ResponseTopicDTO updatedTopic = updateTopicUseCase.update(updateTopicDTO, id, userAuth);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User userAuth){
        deleteTopicUseCase.delete(id, userAuth);
        return ResponseEntity.noContent().build();
    }

}
