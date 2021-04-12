package com.example.projet_vente_voiture.Object;

public class CritereAnnonce {
   private int id;
   private int id_critere;
   private int id_annonce;
   private String valeur;

    public CritereAnnonce(int id, int id_critere, int id_annonce, String valeur) {
        this.id = id;
        this.id_critere = id_critere;
        this.id_annonce = id_annonce;
        this.valeur = valeur;
    }

    public CritereAnnonce(int id_critere, int id_annonce, String valeur) {
        this.id_critere = id_critere;
        this.id_annonce = id_annonce;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_critere() {
        return id_critere;
    }

    public void setId_critere(int id_critere) {
        this.id_critere = id_critere;
    }

    public int getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(int id_annonce) {
        this.id_annonce = id_annonce;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
