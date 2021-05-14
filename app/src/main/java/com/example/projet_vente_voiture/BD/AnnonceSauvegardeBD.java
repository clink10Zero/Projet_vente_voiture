package com.example.projet_vente_voiture.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceSauvegarde;

import java.util.ArrayList;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ANNONCE_ANNONCE_SAUVEGARDE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_AUTEUR_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_DATE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_DESCCRITPION_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_ANNONCE_SAUVEGARDE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_LIEU_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PRIX_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_TITRE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_UTILISATEUR_ANNONCE_SAUVEGARDE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_ANNONCE_SAUVEGARDE;

public class AnnonceSauvegardeBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private MaBaseSQLite maBaseSQLite;

    public AnnonceSauvegardeBD(Context context) {
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

    public void insertAnnonceSauvegarde(AnnonceSauvegarde annonce) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_UTILISATEUR_ANNONCE_SAUVEGARDE, annonce.getUtilisateurid());
        values.put(COL_ANNONCE_ANNONCE_SAUVEGARDE, annonce.getAnnonce_id());
        int id = (int) bd.insert(TABLE_ANNONCE_SAUVEGARDE,null,values);
        annonce.setId(id);
        close();
    }

    public void removeAnnonceSauvegardeWitID(int id) {
        open();
        bd.delete(TABLE_ANNONCE_SAUVEGARDE, COL_ID_ANNONCE_SAUVEGARDE + " = " + id, null);
        close();
    }

    public void removeAnnonceSauvegardeWithUserAndAnnonceID(int user, int annonce) {
        open();
        bd.delete(TABLE_ANNONCE_SAUVEGARDE, COL_ANNONCE_ANNONCE_SAUVEGARDE + " = " + annonce + " AND " + COL_UTILISATEUR_ANNONCE_SAUVEGARDE + " = " + user, null);
        close();
    }

    public ArrayList<AnnonceSauvegarde> getAnnonceSauvegardeByUserId(int user) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE_SAUVEGARDE +" WHERE "+ COL_UTILISATEUR_ANNONCE_SAUVEGARDE +" = "+ user,null);
        ArrayList<AnnonceSauvegarde> list = cursorToAnnonceSauvegardes(c);
        if(list==null){
            return null;
        }
        close();
        return list;
    }

    public ArrayList<Integer> getIDAnnonceSauvegardeByUserId(int user) {
        ArrayList<AnnonceSauvegarde> list = getAnnonceSauvegardeByUserId(user);
        ArrayList<Integer> resList=null;
        if(list!=null){
            resList = new ArrayList<>();
            for (AnnonceSauvegarde as : list){
                resList.add(as.getAnnonce_id());
            }
        }

        return resList;
    }

    public ArrayList<AnnonceSauvegarde> getAnnonceSauvegardeByAnnonceId(int annonce) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE_SAUVEGARDE +" WHERE "+ COL_ANNONCE_ANNONCE_SAUVEGARDE +" = "+ annonce,null);
        ArrayList<AnnonceSauvegarde> list = cursorToAnnonceSauvegardes(c);
        if(list==null){
            return null;
        }
        close();
        return list;
    }

    private ArrayList<AnnonceSauvegarde> cursorToAnnonceSauvegardes(Cursor c) {
        if (c.getCount() == 0)
            return null;

        ArrayList<AnnonceSauvegarde> res = new ArrayList<>();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(0);
            int utilisateur = c.getInt(1);
            int annonce = c.getInt(2);

            res.add(new AnnonceSauvegarde(id,utilisateur,annonce));
            c.moveToNext();
        }
        c.close();
        return res;
    }


}
