package com.example.projet_vente_voiture.Object;

public class AnnonceSauvegarde {
    int id;
    int annonce_id;
    int utilisateurid;

    public AnnonceSauvegarde( int utilisateurid, int annonce_id) {
        this.annonce_id = annonce_id;
        this.utilisateurid = utilisateurid;
    }
    public AnnonceSauvegarde(int id, int  utilisateurid, int annonce_id) {
        this.id = id;
        this.annonce_id = annonce_id;
        this.utilisateurid = utilisateurid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnonce_id() {
        return annonce_id;
    }

    public void setAnnonce_id(int annonce_id) {
        this.annonce_id = annonce_id;
    }

    public int getUtilisateurid() {
        return utilisateurid;
    }

    public void setUtilisateurid(int utilisateurid) {
        this.utilisateurid = utilisateurid;
    }
}
