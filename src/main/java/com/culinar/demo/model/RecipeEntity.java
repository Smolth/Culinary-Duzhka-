package com.culinar.demo.model;

import com.culinar.demo.description.StringArrayType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "recept")
@TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
)
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_generator")
    @SequenceGenerator(name="recipe_generator", sequenceName = "recipe_seq", allocationSize = 1, initialValue = 109)
    private int id;
    private String head;
    private String image;

    @Column(name = "ingredients")
    @Type(type = "string-array")
    private String[] ingredients;
    @Type(type = "string-array")
    private String[] preparation;
    @Type(type = "string-array")
    private String[] recipe;
    private String adding;

    public RecipeEntity(int id, String head, String image, String[] ingredients, String[] preparation, String[] recipe, String adding) {
        this.id = id;
        this.head = head;
        this.image = image;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.recipe = recipe;
        this.adding = adding;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecipeEntity entity = (RecipeEntity) o;
        return id != 0 && Objects.equals(id, entity.id);
    }

}
