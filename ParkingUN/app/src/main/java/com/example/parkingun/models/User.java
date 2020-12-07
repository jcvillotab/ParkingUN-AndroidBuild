package com.example.parkingun.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private long identification;
    private String passwordF;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIdentification() {
        return identification;
    }

    public void setIdentification(long identification) {
        this.identification = identification;
    }

    public String getPasswordF() {
        return passwordF;
    }

    public void setPasswordF(String passwordF) {
        this.passwordF = passwordF;
    }
}
