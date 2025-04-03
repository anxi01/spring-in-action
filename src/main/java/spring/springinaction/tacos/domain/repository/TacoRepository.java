package spring.springinaction.tacos.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import spring.springinaction.tacos.domain.model.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
