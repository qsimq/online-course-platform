package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsimaZhorabayevaEnrollmentRepository extends JpaRepository<AsimaZhorabayevaEnrollment, Long> {
    Optional<AsimaZhorabayevaEnrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<AsimaZhorabayevaEnrollment> findByStudentId(Long studentId);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}