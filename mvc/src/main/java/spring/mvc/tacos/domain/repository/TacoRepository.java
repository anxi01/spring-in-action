package spring.mvc.tacos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.mvc.tacos.domain.model.Taco;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
