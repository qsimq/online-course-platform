package kz.iitu.onlinecourseplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsimaZhorabayevaLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    private Integer duration;

    private String videoUrl;

    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private AsimaZhorabayevaCourse course;
}