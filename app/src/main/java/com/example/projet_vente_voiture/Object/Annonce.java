package com.example.projet_vente_voiture.Object;

public class Annonce {
   private int id;
   private int id_utilisateur;
   private String titre;
   private String lieu;
   private String descritpion;
   private int prix;

    public Annonce(int id, int id_utilisateur, String titre,String descritpion, String lieu,  int prix) {
        this.id = id;
        this.id_utilisateur = id_utilisateur;
        this.titre = titre;
        this.lieu = lieu;
        this.descritpion = descritpion;
        this.prix = prix;
    }

    public Annonce(int id_utilisateur, String titre,String descritpion, String lieu,  int prix) {
        this.id_utilisateur = id_utilisateur;
        this.titre = titre;
        this.lieu = lieu;
        this.descritpion = descritpion;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
