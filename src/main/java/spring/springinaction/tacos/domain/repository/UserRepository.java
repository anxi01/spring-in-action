package spring.springinaction.tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springinaction.tacos.domain.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
