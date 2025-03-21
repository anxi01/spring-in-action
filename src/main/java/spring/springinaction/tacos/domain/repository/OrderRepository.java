package spring.springinaction.tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springinaction.tacos.domain.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
