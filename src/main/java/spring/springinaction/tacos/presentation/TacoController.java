package spring.springinaction.tacos.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.springinaction.tacos.domain.model.Taco;
import spring.springinaction.tacos.domain.repository.TacoRepository;

@RestController
@RequestMapping(path = "/design",
        produces = "application/json")
@RequiredArgsConstructor
public class TacoController {

    private final TacoRepository tacoRepository;

    // 페이지 처리하기
    @GetMapping("/recent")
    public Flux<Taco> recentTacos(@RequestParam int pageSize) {
        return tacoRepository.findAll().take(pageSize);
    }

    // 단일 값 반환하기
    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable long id) {
        return tacoRepository.findById(id);
    }

    // 리액티브하게 입력 처리하기
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
        return tacoRepository.saveAll(tacoMono).next();
    }

}
