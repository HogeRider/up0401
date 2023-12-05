package com.example.restfullapicandyshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "History")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_History")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Treaty_ID")
    private Treaty treaty;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    private Product product;

    @NotNull
    @Column(name = "Product_Count_History")
    private Integer productCountHistory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Treaty getTreaty() {
        return treaty;
    }

    public void setTreaty(Treaty treaty) {
        this.treaty = treaty;
    }

    public Integer getProductCountHistory() {
        return productCountHistory;
    }

    public void setProductCountHistory(Integer productCountHistory) {
        this.productCountHistory = productCountHistory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
