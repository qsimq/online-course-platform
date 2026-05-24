package kz.iitu.onlinecourseplatform.service;

import kz.iitu.onlinecourseplatform.entity.ZhoraUser;
import kz.iitu.onlinecourseplatform.repository.ZhoraUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZhoraUserService {

    @Autowired
    private ZhoraUserRepository userRepository;

    public ZhoraUser register(ZhoraUser user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public ZhoraUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}