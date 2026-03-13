package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.TopicRegisterDTO;
import br.com.api.forum_hub.dtos.TopicResponseDTO;
import br.com.api.forum_hub.dtos.TopicUpdateDTO;
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

@RequestMapping("/topics")
@RestController
public class TopicController {
    private final RegisterTopicUseCase registerTopicUseCase;
    private final TopicRepository repository;
    private final FindTopicUseCase findTopicUseCase;
    private final UpdateTopicUseCase updateTopicUseCase;
    private final DeleteTopicUseCase deleteTopicUseCase;

    public TopicController(RegisterTopicUseCase registerTopicUseCase, TopicRepository repository, FindTopicUseCase findTopicUseCase, UpdateTopicUseCase updateTopicUseCase, DeleteTopicUseCase deleteTopicUseCase) {
        this.registerTopicUseCase = registerTopicUseCase;
        this.repository = repository;
        this.findTopicUseCase = findTopicUseCase;
        this.updateTopicUseCase = updateTopicUseCase;
        this.deleteTopicUseCase = deleteTopicUseCase;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicResponseDTO> register(@RequestBody @Valid TopicRegisterDTO data, UriComponentsBuilder uriBuilder, @AuthenticationPrincipal User user){
        TopicResponseDTO responseTopic = registerTopicUseCase.register(data, user);

        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(responseTopic.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseTopic);
    }

    @GetMapping
    public ResponseEntity<List<TopicResponseDTO>> listAll(@AuthenticationPrincipal User userAuth){
        List<Topic> allTopics = repository.findAllByAuthor(userAuth);

        List<TopicResponseDTO> response = allTopics.stream().map(TopicResponseDTO::new).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> findTopic(@PathVariable Long id, @AuthenticationPrincipal User userAuth){
        return ResponseEntity.ok(findTopicUseCase.find(id, userAuth));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> update(@PathVariable Long id, @RequestBody TopicUpdateDTO topicUpdateDTO, @AuthenticationPrincipal User userAuth){
        TopicResponseDTO updatedTopic = updateTopicUseCase.update(topicUpdateDTO, id, userAuth);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User userAuth){
        deleteTopicUseCase.delete(id, userAuth);
        return ResponseEntity.noContent().build();
    }

}
