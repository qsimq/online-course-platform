package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsimaZhorabayevaFileRepository
        extends JpaRepository<AsimaZhorabayevaFile, Long> {
}