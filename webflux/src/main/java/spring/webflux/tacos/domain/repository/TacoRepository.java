package spring.webflux.tacos.domain.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.webflux.tacos.domain.model.Taco;

import java.util.UUID;

@Repository
public interface TacoRepository {

    Flux<Taco> findAll();
    Mono<Taco> findById(UUID id);
    Flux<Taco> saveAll(Mono<Taco> taco);
}
