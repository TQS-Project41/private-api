package com.example.demo.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Table;

@Table(name="user")
@Entity
public class User implements UserDetails {
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
    private LocalDate birthday;

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

    public User(String email, String name, String password, LocalDate birthday, String phoneNumber, boolean isAdmin, boolean isStaff) {
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


    public LocalDate getBirthday() {
        return birthday;
    }


    public void setBirthday(LocalDate birthday) {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }  
    
}
