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


@Service
@AllArgsConstructor
@NoArgsConstructor
public class DBUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CloudUser cloudUser = userRepository.findUserByLogin(username);
        if (cloudUser == null) {
            throw new UserNotFoundException("");
        }
        return User.builder()
                .username(cloudUser.getLogin())
                .password(passwordEncoder.encode(cloudUser.getPassword()))
                .build();
    }
}
