package spring.springinaction.tacos.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.springinaction.tacos.domain.model.Order;
import spring.springinaction.tacos.domain.model.User;
import spring.springinaction.tacos.domain.repository.OrderRepository;
import spring.springinaction.tacos.request.ProcessOrderRequest;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Void> processOrder(@RequestBody ProcessOrderRequest request, @AuthenticationPrincipal User user) {
        log.info("Processing order request : {}", request);
        log.info("User : {}", user);

        Order order = request.toOrder();
        order.setUser(user);
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }
}
