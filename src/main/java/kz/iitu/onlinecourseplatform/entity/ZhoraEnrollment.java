package kz.iitu.onlinecourseplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhoraEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private ZhoraUser student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private ZhoraCourse course;

    private LocalDateTime enrolledAt;
    private Double progress = 0.0;
    private Boolean completed = false;

    @PrePersist
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
    }
}