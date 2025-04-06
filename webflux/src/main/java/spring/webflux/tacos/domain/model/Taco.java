package spring.webflux.tacos.domain.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Taco {

    private UUID id;

    private Date createdAt;

    private String name;

    private List<Ingredient> ingredients;

}
