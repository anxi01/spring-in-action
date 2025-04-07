package spring.webflux.tacos;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.webflux.tacos.domain.model.Ingredient;
import spring.webflux.tacos.domain.model.Taco;
import spring.webflux.tacos.presentation.TacoController;
import spring.webflux.tacos.domain.repository.TacoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TacoControllerTest {

    @Test
    public void 최신_타코를_반환한다() {
        Taco[] tacos = new Taco[]{
                testTaco(1L), testTaco(2L),
                testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L),
                testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L)
        };
        Flux<Taco> tacoFlux = Flux.just(tacos);
        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);
        Mockito.when(tacoRepository.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient = WebTestClient.bindToController(
                new TacoController(tacoRepository)
        ).build();

        testClient.get()
                .uri("/tacos/recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo(tacos[0].getName())
                .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
                .jsonPath("$[1].name").isEqualTo(tacos[1].getName());
    }

    @Test
    public void 타코를_저장한다() {
        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);
        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(null));

        Taco savedTaco = testTaco(null);
        UUID uuid = UUID.randomUUID();
        savedTaco.setId(uuid);
        Mono<Taco> savedTacoMono = Mono.just(savedTaco);

        Mockito.when(tacoRepository.save(Mockito.any())).thenReturn(savedTacoMono);

        WebTestClient testClient = WebTestClient.bindToController(
                new TacoController(tacoRepository)
        ).build();

        testClient.post()
                .uri("/tacos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTacoMono, Taco.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Taco.class)
                .isEqualTo(savedTaco);
    }

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(UUID.randomUUID());
        taco.setName("Taco " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }
}
