package kz.iitu.onlinecourseplatform.service;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaUser;
import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsimaZhorabayevaUserService {

    @Autowired
    private AsimaZhorabayevaUserRepository userRepository;

    public AsimaZhorabayevaUser register(AsimaZhorabayevaUser user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public AsimaZhorabayevaUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}