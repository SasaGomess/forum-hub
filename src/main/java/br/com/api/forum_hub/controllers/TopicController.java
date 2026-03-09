package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.RegisterTopicData;
import br.com.api.forum_hub.dtos.ResponseTopicDTO;
import br.com.api.forum_hub.services.RegisterTopic;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequestMapping("/topicos")
@RestController
public class TopicController {
    private RegisterTopic registerTopic;

    public TopicController(RegisterTopic registerTopic) {
        this.registerTopic = registerTopic;
    }

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid RegisterTopicData data, UriComponentsBuilder uriBuilder){
        ResponseTopicDTO responseTopic = registerTopic.register(data);

        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(responseTopic.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseTopic);
    }


}
