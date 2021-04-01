package com.example.projet_vente_voiture.Object;

public class Photo {
   private int id;
   private int id_annonce;
   private String chemin;

    public Photo(int id, int id_annonce, String chemin) {
        this.id = id;
        this.id_annonce = id_annonce;
        this.chemin = chemin;
    }

    public Photo(int id_annonce, String chemin) {
        this.id_annonce = id_annonce;
        this.chemin = chemin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(int id_annonce) {
        this.id_annonce = id_annonce;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }
}
