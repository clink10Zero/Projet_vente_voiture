package com.example.projet_vente_voiture.Object;

public class Annonce {
   private int id;
   private int id_auteur;
   private String titre;
   private String lieu;
   private String descritpion;
   private int prix;
   private String date;

    public Annonce(int id, int id_auteur, String titre, String descritpion, String lieu, int prix, String date) {
        this.id = id;
        this.id_auteur = id_auteur;
        this.titre = titre;
        this.lieu = lieu;
        this.descritpion = descritpion;
        this.prix = prix;
        this.date=date;
    }

    public Annonce(int id_auteur, String titre, String descritpion, String lieu, int prix, String date) {
        this.id_auteur = id_auteur;
        this.titre = titre;
        this.lieu = lieu;
        this.descritpion = descritpion;
        this.prix = prix;
        this.date=date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_auteur() {
        return id_auteur;
    }

    public void setId_auteur(int id_auteur) {
        this.id_auteur = id_auteur;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
