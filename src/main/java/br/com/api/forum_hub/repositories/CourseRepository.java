package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.models.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);

    Course findByName(String course);
}
