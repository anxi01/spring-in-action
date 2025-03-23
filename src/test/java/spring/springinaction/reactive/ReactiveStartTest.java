package spring.springinaction.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

class ReactiveStartTest {

    @Test
    void 명령형_프로그래밍_예시() {
        String name = "Seongmin";
        String capitalName = name.toUpperCase();
        String greeting = "Hello " + capitalName + "!";
        System.out.println(greeting);
    }

    @Test
    void 라액티브_프로그래밍_예시() {
        Mono.just("Seongmin")
                .map(String::toUpperCase)
                .map(cn -> "Hello " + cn + "!")
                .subscribe(System.out::println);
    }

    @Test
    void 객체로부터_생성하기() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    void 배열로부터_생성하기() {
        String[] fruits = {"Apple", "Orange", "Grape", "Banana", "Strawberry"};
        Flux<String> fruitFlux = Flux.fromArray(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    void Iterable로부터_생성하기() {
        List<String> fruitList = List.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFlux = Flux.fromIterable(fruitList);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    void 스트림으로부터_생성하기() {
        Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFlux = Flux.fromStream(fruitStream);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    void range를_사용하여_생성하기() {
        Flux<Integer> intervalFlux = Flux.range(1, 5);

        StepVerifier.create(intervalFlux)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    void interval를_사용하여_생성하기() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1L));

        StepVerifier.create(intervalFlux)
                .expectNext(0L, 1L, 2L, 3L, 4L, 5L)
                .verifyComplete();
    }
}
