package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AsimaZhorabayevaUserRepository extends JpaRepository<AsimaZhorabayevaUser, Long> {
    Optional<AsimaZhorabayevaUser> findByEmail(String email);
    boolean existsByEmail(String email);
}