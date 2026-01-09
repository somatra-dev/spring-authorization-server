package auth.res_server.demo.service.impl;

import auth.res_server.demo.config.CustomUserDetails;
import auth.res_server.demo.domain.User;
import auth.res_server.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        if (!user.getEmailVerified()) {
            throw new DisabledException("Email not verified. Please verify your email before logging in.");
        }

        return new CustomUserDetails(user);
    }
}