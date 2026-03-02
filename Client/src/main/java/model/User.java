package model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String nom;
    private String email;
    private String password;

    public User() {
    }

    public User(int id, String nom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    public User(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
