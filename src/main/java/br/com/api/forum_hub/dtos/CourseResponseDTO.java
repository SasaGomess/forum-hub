package br.com.api.forum_hub.dtos;

import br.com.api.forum_hub.models.Course;

public record CourseResponseDTO (Long id, String category, String name) {
    public CourseResponseDTO(Course course){
        this(course.getId(), course.getCategory(), course.getName());
    }
}
