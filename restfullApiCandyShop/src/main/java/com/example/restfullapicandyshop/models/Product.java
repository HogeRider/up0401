package com.example.restfullapicandyshop.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Product")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ProductType_ID")
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "ShelfLife_ID")
    private ShelfLife shelfLife;

    @ManyToOne
    @JoinColumn(name = "Ingredient_ID")
    private Ingredient ingredient;

    @DecimalMin(value = "0.0001")
    @Column(name = "Product_Cost")
    private BigDecimal productCost;

    @NotNull
    @Column(name = "ProductCount")
    private Integer productCount;

    @NotBlank
    @Column(name = "ProductName", unique = true)
    private String productName;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ShelfLife getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(ShelfLife shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
