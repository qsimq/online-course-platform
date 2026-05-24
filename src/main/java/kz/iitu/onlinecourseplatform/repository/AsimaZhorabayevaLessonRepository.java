package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsimaZhorabayevaLessonRepository
        extends JpaRepository<AsimaZhorabayevaLesson, Long> {

    List<AsimaZhorabayevaLesson> findByCourseIdOrderByOrderIndex(Long courseId);
}