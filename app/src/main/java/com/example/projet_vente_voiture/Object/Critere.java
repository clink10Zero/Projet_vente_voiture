package com.example.projet_vente_voiture.Object;

public class Critere {
   private int id;
   private String nom;
   private int type;

    public Critere(int id, String nom, int type) {
        this.id = id;
        this.nom = nom;
        this.type = type;
    }

    public Critere(String nom, int type) {
        this.nom = nom;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
