package com.example.restfullapicandyshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "IngerdientTypes")
public class IngredientType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IngredientType")
    private Integer id;

    @NotBlank
    @Column(name = "IngredientTypeName", unique = true)
    private String ingredientTypeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIngredientTypeName() {
        return ingredientTypeName;
    }

    public void setIngredientTypeName(String ingredientTypeName) {
        this.ingredientTypeName = ingredientTypeName;
    }
}
