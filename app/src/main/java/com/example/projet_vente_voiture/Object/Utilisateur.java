package com.example.projet_vente_voiture.Object;

public class Utilisateur {
    public static int PROFESSIONEL =0;
    public static int PARTICULIER =1;

    private int id;
    private String prenom;
    private String nom;
    private String mail;
    private String mdp;
    private int professionel;

    public Utilisateur(int id, String prenom, String nom, String mail, String mdp, int professionel) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.mail = mail;
        this.mdp = mdp;
        this.professionel = professionel;
    }

    public Utilisateur(String prenom, String nom, String mail, String mdp, int professionel) {
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

    public int getProfessionel() {
        return professionel;
    }

    public void setProfessionel(int professionel) {
        this.professionel = professionel;
    }
}
