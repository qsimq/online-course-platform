package kz.iitu.onlinecourseplatform.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsimaZhorabayevaEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AsimaZhorabayevaUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private AsimaZhorabayevaCourse course;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    private Integer progress;

    @PrePersist
    protected void onCreate() {
        enrolledAt = LocalDateTime.now();
        progress = 0;
        status = EnrollmentStatus.ACTIVE;
    }

    public enum EnrollmentStatus {
        ACTIVE, COMPLETED, CANCELLED
    }
}