package spring.springinaction.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

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
}
