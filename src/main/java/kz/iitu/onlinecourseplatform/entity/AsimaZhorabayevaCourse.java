package kz.iitu.onlinecourseplatform.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsimaZhorabayevaCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String instructor;

    private Double price;

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private AsimaZhorabayevaCategory category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<AsimaZhorabayevaLesson> lessons;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<AsimaZhorabayevaEnrollment> enrollments;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = Status.DRAFT;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Status {
        DRAFT, PUBLISHED, ARCHIVED
    }
}