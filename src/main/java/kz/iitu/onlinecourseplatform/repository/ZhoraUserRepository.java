package kz.iitu.onlinecourseplatform.repository;

import kz.iitu.onlinecourseplatform.entity.ZhoraUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ZhoraUserRepository extends JpaRepository<ZhoraUser, Long> {
    Optional<ZhoraUser> findByEmail(String email);
    boolean existsByEmail(String email);
}