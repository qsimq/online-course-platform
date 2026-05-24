package kz.iitu.onlinecourseplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsimaZhorabayevaCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;

    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private AsimaZhorabayevaUser instructor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private AsimaZhorabayevaCategory category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<AsimaZhorabayevaLesson> lessons = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
