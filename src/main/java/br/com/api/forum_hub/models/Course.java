package br.com.api.forum_hub.models;

import br.com.api.forum_hub.dtos.CourseRequestDTO;
import jakarta.persistence.*;
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
    @Column(unique = true)
    private String name;
    private String category;

    @OneToMany
    private List<Topic> topics = new ArrayList<>();

    public Course(CourseRequestDTO data) {
        this.name = data.name();
        this.category = data.category();
    }
}
