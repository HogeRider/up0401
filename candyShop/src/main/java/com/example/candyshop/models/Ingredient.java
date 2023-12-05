package com.example.candyshop.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Ingredient")
    private Integer id;

    @Column(name = "Ingredient_Article", unique = true)
    private int ingredientArticle;

    @NotBlank
    @Column(name = "Ingredient_Name", unique = true)
    private String ingredientName;

    @ManyToOne
    @JoinColumn(name = "IngredientType_ID")
    private IngredientType ingredientType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIngredientArticle() {
        return ingredientArticle;
    }

    public void setIngredientArticle(int ingredientArticle) {
        this.ingredientArticle = ingredientArticle;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }
}
