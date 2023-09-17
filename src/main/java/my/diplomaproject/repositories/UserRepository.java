package my.diplomaproject.repositories;

import my.diplomaproject.domain.CloudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CloudUser, Long> {

    Optional<CloudUser> findUserByLogin(String login);

}
