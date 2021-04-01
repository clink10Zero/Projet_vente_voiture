package com.example.projet_vente_voiture.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.Utilisateur;

import java.util.ArrayList;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_UTILISATEUR;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_MAIL_UTILISATEUR;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_MDP_UTILISATEUR;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_NOM_UTILISATEUR;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PRENOM_UTILISATEUR;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PROFESSIONNEL_UTILISATEUR;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_UTILISATEUR;

public class UtilisateurBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private MaBaseSQLite maBaseSQLite;

    public UtilisateurBD(Context context) {
        maBaseSQLite = new MaBaseSQLite(context, NOM_BD, null, VERSION_BD);
    }

    private void open() {
        bd = maBaseSQLite.getWritableDatabase();
    }

    private void close() {
        bd.close();
    }

    public SQLiteDatabase getBD() {
        return bd;
    }

    public void insertUtilisateur(Utilisateur utilisateur) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_NOM_UTILISATEUR, utilisateur.getNom());
        values.put(COL_PRENOM_UTILISATEUR, utilisateur.getPrenom());
        values.put(COL_MAIL_UTILISATEUR, utilisateur.getMail());
        values.put(COL_MDP_UTILISATEUR, utilisateur.getMdp());
        values.put(COL_PROFESSIONNEL_UTILISATEUR, utilisateur.getProfessionel());
        int id = (int) bd.insert(TABLE_UTILISATEUR,null,values);
        utilisateur.setId(id);
        close();
    }

    public void updateUtilisateur(int id, Utilisateur utilisateur) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_NOM_UTILISATEUR, utilisateur.getNom());
        values.put(COL_PRENOM_UTILISATEUR, utilisateur.getPrenom());
        values.put(COL_MAIL_UTILISATEUR, utilisateur.getMail());
        values.put(COL_MDP_UTILISATEUR, utilisateur.getMdp());
        values.put(COL_PROFESSIONNEL_UTILISATEUR, utilisateur.getProfessionel());
        bd.update(TABLE_UTILISATEUR, values, COL_ID_UTILISATEUR + " = " + id, null);
    }

    public void removeUtilisateurWithID(int id) {
        open();
        bd.delete(TABLE_UTILISATEUR, COL_ID_UTILISATEUR + " = " + id, null);
        close();
    }
    public Utilisateur getUtilisateurById(int utilisateur) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_UTILISATEUR +" WHERE "+ COL_ID_UTILISATEUR +" = "+ utilisateur,null);
        List<Utilisateur> list = cursorToUtilisateurs(c);
        if(list==null){
            return null;
        }
        close();
        return list.get(0);
    }

    public Utilisateur getUtilisateurByMail(String mail) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_UTILISATEUR +" WHERE "+ COL_MAIL_UTILISATEUR +" = "+ mail,null);
        List<Utilisateur> list = cursorToUtilisateurs(c);
        if(list==null){
            return null;
        }
        close();
        return list.get(0);
    }

   private List<Utilisateur> cursorToUtilisateurs(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<Utilisateur> res = new ArrayList<>();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(0);
            String prenom = c.getString(1);
            String nom = c.getString(2);
            String mail = c.getString(3);
            String mdp = c.getString(4);
            int professionel = c.getInt(5);

            res.add(new Utilisateur(id,prenom,nom,mail,mdp,professionel));
            c.moveToNext();
        }
        c.close();
        return res;
    }

}

