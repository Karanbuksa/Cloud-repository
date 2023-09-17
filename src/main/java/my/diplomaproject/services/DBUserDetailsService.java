package my.diplomaproject.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import my.diplomaproject.domain.CloudUser;
import my.diplomaproject.exceptions.UserNotFoundException;
import my.diplomaproject.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class DBUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CloudUser> cloudUser = userRepository.findUserByLogin(username);
        if (cloudUser.isEmpty()) {
            throw new UserNotFoundException("");
        }
        return User.builder()
                .username(cloudUser.get().getLogin())
                .password(passwordEncoder.encode(cloudUser.get().getPassword()))
                .build();
    }
}
