package spring.mvc.tacos.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.mvc.tacos.domain.model.Ingredient;
import spring.mvc.tacos.domain.model.Taco;

import java.util.List;

@DataJpaTest
class TacoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TacoRepository tacoRepository;

    @Test
    void PRIVATE_생성자에서_프록시객체가_생성된다() {
        // 1. 재료 데이터 저장
        Ingredient ingredient = new Ingredient("ING1", "Lettuce", Ingredient.Type.VEGGIES);
        entityManager.persist(ingredient);

        // 2. 타코 데이터 저장
        Taco taco = new Taco();
        taco.setName("Veggie Taco");
        taco.setIngredients(List.of(ingredient));
        entityManager.persist(taco);
        entityManager.flush();
        entityManager.clear();  // 1차 캐시 초기화 (프록시 확인)

        // 3. Taco 조회 (Lazy 로딩 확인)
        Taco foundTaco = tacoRepository.findById(taco.getId()).orElseThrow();
        System.out.println(">>> Taco 조회 완료");

        // 4. Ingredient 프록시 여부 확인
        List<Ingredient> ingredients = foundTaco.getIngredients();
        System.out.println(">>> ingredients 클래스: " + ingredients.getClass().getName());
        System.out.println(">>> 첫 번째 ingredient 클래스: " + ingredients.get(0).getClass().getName());

        // 5. 실제 필드 접근 (강제 초기화)
        System.out.println(">>> 첫 번째 ingredient ID: " + ingredients.get(0).getId());
    }
}
