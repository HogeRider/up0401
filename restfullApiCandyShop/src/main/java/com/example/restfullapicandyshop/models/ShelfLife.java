package com.example.restfullapicandyshop.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name = "ShelfLifes")
public class ShelfLife {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ShelfLife")
    private Integer id;

    @NotBlank
    @Column(name = "ShelfLifeYear", unique = true)
    private String shelfLifeYear;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShelfLifeYear() {
        return shelfLifeYear;
    }

    public void setShelfLifeYear(String shelfLifeYear) {
        this.shelfLifeYear = shelfLifeYear;
    }
}