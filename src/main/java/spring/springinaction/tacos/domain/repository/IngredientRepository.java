package spring.springinaction.tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springinaction.tacos.domain.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
