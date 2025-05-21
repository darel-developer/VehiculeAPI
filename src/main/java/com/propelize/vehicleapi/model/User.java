package com.propelize.vehicleapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id // Indique que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Attributs représentant les propriétés de l'utilisateur
    private String name;
    private String password;

    // Constructeurs
    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters et Setters
    public Long getId() {
        return id;
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
}