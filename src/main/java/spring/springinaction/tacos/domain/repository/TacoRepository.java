package spring.springinaction.tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springinaction.tacos.domain.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
