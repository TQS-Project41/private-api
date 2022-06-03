package com.example.demo.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "email é obrigatório")
    private String email;

    @Column
    @NotNull(message = "name é obrigatório")
    private String name;
    
    @Column
    @NotNull(message = "password é obrigatório")
    private String password;

    @Column
    private Date birthday;

    @Column
    @NotNull(message = "phoneNumber é obrigatório")
    private String phoneNumber;

    @Column
    @NotNull(message = "IsActive é obrigatório")
    private boolean isAdmin;

    @Column
    @NotNull(message = "isStaff é obrigatório")
    private boolean isStaff;

    public User() {
    }

    public User(String email, String name, String password, Date birthday, String phoneNumber, boolean isAdmin, boolean isStaff) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.isStaff = isStaff;
    }  


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Date getBirthday() {
        return birthday;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public boolean getAdmin() {
        return isAdmin;
    }


    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    public boolean getStaff() {
        return isStaff;
    }


    public void setStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    
    
    
}
