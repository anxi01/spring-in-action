package spring.mvc.tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import spring.mvc.tacos.domain.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
