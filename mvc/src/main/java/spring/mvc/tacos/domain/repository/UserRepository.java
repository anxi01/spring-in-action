package spring.mvc.tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import spring.mvc.tacos.domain.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
