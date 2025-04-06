package spring.mvc.tacos.domain.repository;

import org.springframework.data.repository.CrudRepository;
import spring.mvc.tacos.domain.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
