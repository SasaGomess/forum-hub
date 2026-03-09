package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.RegisterTopicData;
import br.com.api.forum_hub.dtos.ResponseTopicDTO;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.repositories.TopicRepository;
import br.com.api.forum_hub.services.FindTopic;
import br.com.api.forum_hub.services.RegisterTopic;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

@RequestMapping("/topicos")
@RestController
public class TopicController {
    private RegisterTopic registerTopic;
    private TopicRepository repository;
    private FindTopic findTopic;

    public TopicController(RegisterTopic registerTopic, TopicRepository repository, FindTopic findTopic) {
        this.registerTopic = registerTopic;
        this.repository = repository;
        this.findTopic = findTopic;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseTopicDTO> register(@RequestBody @Valid RegisterTopicData data, UriComponentsBuilder uriBuilder){
        ResponseTopicDTO responseTopic = registerTopic.register(data);

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
        return ResponseEntity.ok(findTopic.find(id));
    }

    



}
