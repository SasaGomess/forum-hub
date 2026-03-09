package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.RegisterTopicDTO;
import br.com.api.forum_hub.dtos.ResponseTopicDTO;
import br.com.api.forum_hub.dtos.UpdateTopicDTO;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.repositories.TopicRepository;
import br.com.api.forum_hub.services.DeleteTopicUseCase;
import br.com.api.forum_hub.services.FindTopicUseCase;
import br.com.api.forum_hub.services.RegisterTopicUseCase;
import br.com.api.forum_hub.services.UpdateTopicUseCase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseTopicDTO> register(@RequestBody @Valid RegisterTopicDTO data, UriComponentsBuilder uriBuilder){
        ResponseTopicDTO responseTopic = registerTopicUseCase.register(data);

        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(responseTopic.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseTopic);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTopicDTO>> listAll(){
        List<Topic> allTopics = repository.findAll();

        List<ResponseTopicDTO> response = allTopics.stream().map(ResponseTopicDTO::new).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTopicDTO> findTopic(@PathVariable Long id){
        return ResponseEntity.ok(findTopicUseCase.find(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTopicDTO> update(@PathVariable Long id, @RequestBody UpdateTopicDTO updateTopicDTO){
        ResponseTopicDTO updatedTopic = updateTopicUseCase.update(updateTopicDTO, id);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteTopicUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
