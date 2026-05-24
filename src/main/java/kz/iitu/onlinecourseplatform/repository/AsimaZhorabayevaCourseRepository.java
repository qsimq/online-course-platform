package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsimaZhorabayevaCourseRepository
        extends JpaRepository<AsimaZhorabayevaCourse, Long> {

    Page<AsimaZhorabayevaCourse> findByTitleContainingIgnoreCase(
            String title, Pageable pageable);

    Page<AsimaZhorabayevaCourse> findByStatus(
            AsimaZhorabayevaCourse.Status status, Pageable pageable);

    Page<AsimaZhorabayevaCourse> findByTitleContainingIgnoreCaseAndStatus(
            String title,
            AsimaZhorabayevaCourse.Status status,
            Pageable pageable);
}