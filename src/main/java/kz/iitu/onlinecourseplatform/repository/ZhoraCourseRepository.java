package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.ZhoraCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZhoraCourseRepository extends JpaRepository<ZhoraCourse, Long> {
}
