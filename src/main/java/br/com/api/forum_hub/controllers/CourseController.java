package br.com.api.forum_hub.controllers;

import br.com.api.forum_hub.dtos.CreateCourseDTO;
import br.com.api.forum_hub.dtos.RegisterTopicDTO;
import br.com.api.forum_hub.dtos.ResponseTopicDTO;
import br.com.api.forum_hub.dtos.UpdateTopicDTO;
import br.com.api.forum_hub.models.Course;
import br.com.api.forum_hub.models.Topic;
import br.com.api.forum_hub.repositories.CourseRepository;
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

@RequestMapping("/courses")
@RestController
public class CourseController {

    private CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Course> create(@RequestBody @Valid CreateCourseDTO data, UriComponentsBuilder uriBuilder){
        Course course = new Course(data);
        Course savedCourse = repository.save(course);

        URI uri = uriBuilder.path("/courses/{id}")
                .buildAndExpand(savedCourse.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedCourse);
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
