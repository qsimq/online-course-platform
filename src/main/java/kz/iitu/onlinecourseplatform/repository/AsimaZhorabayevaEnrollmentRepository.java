package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsimaZhorabayevaEnrollmentRepository
        extends JpaRepository<AsimaZhorabayevaEnrollment, Long> {

    List<AsimaZhorabayevaEnrollment> findByUserId(Long userId);

    List<AsimaZhorabayevaEnrollment> findByCourseId(Long courseId);

    Optional<AsimaZhorabayevaEnrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    long countByCourseId(Long courseId);
}