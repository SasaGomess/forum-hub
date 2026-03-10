package br.com.api.forum_hub.models;

import br.com.api.forum_hub.dtos.UpdateTopicDTO;
import br.com.api.forum_hub.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity(name = "Topic")
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String tittle;
    @Column(unique = true)
    private String message;
    private LocalDateTime creationDate;
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "topic")
    private List<Response> responses = new ArrayList<>();


    public void update(UpdateTopicDTO data){
        this.tittle = data.tittle();
        this.message = data.message();
    }
}