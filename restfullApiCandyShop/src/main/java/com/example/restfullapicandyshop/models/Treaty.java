package com.example.restfullapicandyshop.models;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Treaty")
public class Treaty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Treaty")
    private Integer id;

    @NotBlank
    @Size(max = 15)
    @Column(name = "Treaty_Number", unique = true)
    private String treatyNumber;

    @NotNull
    @Column(name = "Date_of_conclusion")
    @Temporal(TemporalType.DATE)
    private Date dateOfConclusion;

    @NotNull
    @Digits(integer = 20, fraction = 4)
    @Column(name = "Amount")
    private BigDecimal amount;

    @NotBlank
    @Column(name = "TreatyContent")
    private String treatyContent;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "User_ID")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTreatyNumber() {
        return treatyNumber;
    }

    public void setTreatyNumber(String treatyNumber) {
        this.treatyNumber = treatyNumber;
    }

    public Date getDateOfConclusion() {
        return dateOfConclusion;
    }

    public void setDateOfConclusion(Date dateOfConclusion) {
        this.dateOfConclusion = dateOfConclusion;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTreatyContent() {
        return treatyContent;
    }

    public void setTreatyContent(String treatyContent) {
        this.treatyContent = treatyContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
