package my.diplomaproject.repositories;

import my.diplomaproject.domain.CloudUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CloudUser, Long> {

    CloudUser findUserByLogin(String login);

}
