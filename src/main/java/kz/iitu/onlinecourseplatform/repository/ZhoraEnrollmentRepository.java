package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.ZhoraEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ZhoraEnrollmentRepository extends JpaRepository<ZhoraEnrollment, Long> {
    Optional<ZhoraEnrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<ZhoraEnrollment> findByStudentId(Long studentId);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}