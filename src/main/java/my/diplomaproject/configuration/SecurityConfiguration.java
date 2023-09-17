package my.diplomaproject.configuration;

import lombok.AllArgsConstructor;
import my.diplomaproject.repositories.UserRepository;
import my.diplomaproject.services.DBUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalAuthentication
@AllArgsConstructor
public class SecurityConfiguration {


    private final AuthenticationConfiguration authenticationConfiguration;
    private UserRepository userRepository;

    @Autowired
    public SecurityConfiguration(AuthenticationConfiguration configuration) {
        this.authenticationConfiguration = configuration;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new DBUserDetailsService(userRepository, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonFilter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager());
        jsonFilter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilter(jsonFilter)
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests.anyRequest().authenticated()
                )

                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
