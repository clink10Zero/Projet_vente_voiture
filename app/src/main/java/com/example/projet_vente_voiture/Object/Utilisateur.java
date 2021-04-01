package com.example.projet_vente_voiture.Object;

public class Utilisateur {
    private int id;
    private String prenom;
    private String nom;
    private String mail;
    private String mdp;
    private Boolean professionel;

    public Utilisateur(int id, String prenom, String nom, String mail, String mdp, Boolean professionel) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.mail = mail;
        this.mdp = mdp;
        this.professionel = professionel;
    }

    public Utilisateur(String prenom, String nom, String mail, String mdp, Boolean professionel) {
        this.prenom = prenom;
        this.nom = nom;
        this.mail = mail;
        this.mdp = mdp;
        this.professionel = professionel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Boolean getProfessionel() {
        return professionel;
    }

    public void setProfessionel(Boolean professionel) {
        this.professionel = professionel;
    }
}
