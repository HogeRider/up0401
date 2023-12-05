package com.example.restfullapicandyshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
@Entity
@Table(name = "Users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_User")
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "First_Name")
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "Second_Name")
    private String secondName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "Middle_Name")
    private String middleName;

    @NotBlank
    @Pattern(regexp = "M|F")
    @Column(name = "Gender")
    private String gender;

    @NotBlank
    @Length(min = 2, max = 32)
    @Column(name = "Login")
    private String login;

    @NotBlank
    @Column(name = "Password")
    private String password;

    @NotBlank
    @Size(max = 16)
    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Email
    @NotBlank
    @Column(name = "Email")
    private String email;

    @ManyToOne()
    @JoinColumn(name = "Role_ID")
    private Role role;


    @NotBlank
    @Column(name = "EncryptKey")
    private String encryptKey;

    @Transient
    private String roleName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

