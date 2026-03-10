package br.com.api.forum_hub.repositories;

import br.com.api.forum_hub.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);
}
