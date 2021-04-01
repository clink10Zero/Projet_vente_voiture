package com.example.projet_vente_voiture.Object;

public class ValeurCritere {
   private int id;
   private int critere;
   private String valeur;

    public ValeurCritere(int id, int critere, String valeur) {
        this.id = id;
        this.critere = critere;
        this.valeur = valeur;
    }

    public ValeurCritere(int critere, String valeur) {
        this.critere = critere;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCritere() {
        return critere;
    }

    public void setCritere(int critere) {
        this.critere = critere;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
