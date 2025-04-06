package spring.mvc.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    /**
     * foodFlux가 characterFlux 다음에 스트리밍을 진행하도록
     * delaySubscription()을 사용해 250ms 이후에 구독 및 데이터를 방출하게 함.
     */
    @Test
    void Flux_결합하기() {
        Flux<String> characterFlux = Flux
                .just("짱구", "철수", "맹구")
                .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux
                .just("제육", "돈까스", "커피")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNext("짱구", "제육", "철수", "돈까스", "맹구", "커피")
                .verifyComplete();
    }

    @Test
    void ZIP으로_결합하기() {
        Flux<String> characterFlux = Flux
                .just("짱구", "철수", "맹구");
        Flux<String> foodFlux = Flux
                .just("제육", "돈까스", "커피");

        Flux<Tuple2<String, String>> mergedFlux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNextMatches(p ->
                    p.getT1().equals("짱구") && p.getT2().equals("제육")
                )
                .expectNextMatches(p ->
                        p.getT1().equals("철수") && p.getT2().equals("돈까스")
                )
                .expectNextMatches(p ->
                        p.getT1().equals("맹구") && p.getT2().equals("커피")
                )
                .verifyComplete();
    }

    @Test
    void ZIP으로_커스텀하게_결합하기() {
        Flux<String> characterFlux = Flux
                .just("짱구", "철수", "맹구");
        Flux<String> foodFlux = Flux
                .just("제육", "돈까스", "커피");

        Flux<String> mergedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " " + f);

        StepVerifier.create(mergedFlux)
                .expectNext("짱구 제육")
                .expectNext("철수 돈까스")
                .expectNext("맹구 커피")
                .verifyComplete();
    }

    @Test
    void 먼저_값을_방출하는_값을_발행하기() {
        Flux<String> characterFlux = Flux
                .just("짱구", "철수", "맹구")
                .delaySubscription(Duration.ofMillis(100));
        Flux<String> foodFlux = Flux
                .just("제육", "돈까스", "커피");

        Flux<String> mergedFlux = Flux.firstWithSignal(characterFlux, foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNext("제육", "돈까스", "커피")
                .verifyComplete();
    }

    @Test
    void 지정된_수의_메시지를_건너뛰기() {
        Flux<String> skipFlux = Flux
                .just("짱구", "철수", "맹구", "유리", "훈이")
                .skip(3);

        StepVerifier.create(skipFlux)
                .expectNext("유리", "훈이")
                .verifyComplete();
    }

    @Test
    void 지정된_수의_메시지만_방출하기() {
        Flux<String> favoriteCharacter = Flux
                .just("짱구", "철수", "맹구", "유리", "훈이")
                .take(3);

        StepVerifier.create(favoriteCharacter)
                .expectNext("짱구", "철수", "맹구")
                .verifyComplete();
    }

    @Test
    void 지정된_조건식의_메시지만_방출하기() {
        Flux<String> favoriteCharacter = Flux
                .just("짱구", "철수", "맹구", "유리", "훈이")
                .filter(k -> !k.equals("훈이"));

        StepVerifier.create(favoriteCharacter)
                .expectNext("짱구", "철수", "맹구", "유리")
                .verifyComplete();
    }

    @Test
    void 중복되지_않는_메시지만_방출하기() {
        Flux<String> duplicateFlux = Flux
                .just("짱구", "철수", "맹구", "짱구", "철수")
                .distinct();

        StepVerifier.create(duplicateFlux)
                .expectNext("짱구", "철수", "맹구")
                .verifyComplete();
    }

    @Test
    void map으로_메시지를_변환하기() {
        Flux<String> animation = Flux
                .just("짱구는 못말려", "철수는 못말려", "맹구는 못말려")
                .map(n -> {
                    String[] split = n.split(" ");
                    return split[0];
                });

        StepVerifier.create(animation)
                .expectNext("짱구는", "철수는", "맹구는")
                .verifyComplete();
    }

    @Test
    void 데이터를_버퍼링하기() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

        StepVerifier.create(bufferedFlux)
                .expectNext(Arrays.asList("Apple", "Orange", "Grape"))
                .expectNext(Arrays.asList("Banana", "Strawberry"))
                .verifyComplete();
    }

    @Test
    void 데이터를_병행으로_버퍼링하기() {
        Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry")
                .buffer(3)
                .flatMap(x ->
                        Flux.fromIterable(x)
                                .map(String::toUpperCase)
                                .subscribeOn(Schedulers.parallel())
                                .log()
                ).subscribe();
    }

    @Test
    void 모든_항목을_리스트로_방출하기() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        Mono<List<String>> fruitListMono = fruitFlux.collectList();

        StepVerifier.create(fruitListMono)
                .expectNext(Arrays.asList("Apple", "Orange", "Grape", "Banana", "Strawberry"))
                .verifyComplete();
    }

    @Test
    void 모든_항목을_맵으로_방출하기() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Orange", "Grape");

        Mono<Map<Character, String>> fruitMapMono = fruitFlux.collectMap(a -> a.charAt(0));

        StepVerifier.create(fruitMapMono)
                .expectNextMatches(map -> map.size() == 3 &&
                        map.get('A').equals("Apple") &&
                        map.get('O').equals("Orange") &&
                        map.get('G').equals("Grape"))
                .verifyComplete();
    }

    @Test
    void 모든_항목이_조건을_충족하는지_검사하기() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Strawberry");

        Mono<Boolean> hasBMono = fruitFlux.all(a -> a.contains("e"));
        StepVerifier.create(hasBMono)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void 일부_항목이_조건을_충족하는지_검사하기() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Strawberry");

        Mono<Boolean> hasAMono = fruitFlux.any(a -> a.contains("A"));
        StepVerifier.create(hasAMono)
                .expectNext(true)
                .verifyComplete();
    }
}
