package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.*;
import br.com.api.forum_hub.models.Course;
import br.com.api.forum_hub.repositories.CourseRepository;
import br.com.api.forum_hub.services.ValidationException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/courses")
@RestController
public class CourseController {

    private final CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CourseResponseDTO> create(@RequestBody @Valid CourseRequestDTO data, UriComponentsBuilder uriBuilder){
        if (repository.existsByName(data.name())) throw new ValidationException("Já existe um curso com esse nome");

        Course course = new Course(data);
        CourseResponseDTO courseDto = new CourseResponseDTO(repository.save(course));

        URI uri = uriBuilder.path("/courses/{id}")
                .buildAndExpand(courseDto.id())
                .toUri();

        return ResponseEntity.created(uri).body(courseDto);
    }

    @GetMapping
    public ResponseEntity<List<Course>> listAll(){
        List<Course> allTopics = repository.findAll();
        return ResponseEntity.ok(allTopics);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
