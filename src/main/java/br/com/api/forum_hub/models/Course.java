package br.com.api.forum_hub.models;

import br.com.api.forum_hub.dtos.CreateCourseDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Course")
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;

    @OneToMany
    private List<Topic> topics = new ArrayList<>();

    public Course(CreateCourseDTO data) {
        this.name = data.name();
        this.category = data.category();
    }
}
