package kz.iitu.onlinecourseplatform.security;

import kz.iitu.onlinecourseplatform.entity.AsimaZhorabayevaUser;
import kz.iitu.onlinecourseplatform.repository.AsimaZhorabayevaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsimaZhorabayevaUserDetailsService implements UserDetailsService {

    private final AsimaZhorabayevaUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AsimaZhorabayevaUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));

        return new User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().name()))
        );
    }
}