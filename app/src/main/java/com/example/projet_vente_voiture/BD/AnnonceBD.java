package com.example.projet_vente_voiture.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.Annonce;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_DESCCRITPION_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_LIEU_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PRIX_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_TITRE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_UTILISATEUR_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_ANNONCE;

public class AnnonceBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private MaBaseSQLite maBaseSQLite;

    public AnnonceBD(Context context) {
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

    public void insertAnnonce(Annonce annonce) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_UTILISATEUR_ANNONCE, annonce.getId_utilisateur());
        values.put(COL_TITRE_ANNONCE, annonce.getTitre());
        values.put(COL_DESCCRITPION_ANNONCE, annonce.getDescritpion());
        values.put(COL_LIEU_ANNONCE, annonce.getLieu());
        values.put(COL_PRIX_ANNONCE, annonce.getPrix());
        int id = (int) bd.insert(TABLE_ANNONCE,null,values);
        annonce.setId(id);
        close();
    }

    public void updateAnnonce(int id, Annonce annonce) {
        open();
        ContentValues values = new ContentValues();
        bd.update(TABLE_ANNONCE, values, COL_ID_ANNONCE + " = " + id, null);
    }

    public void removeAnnonceWithID(int id) {
        open();
        bd.delete(TABLE_ANNONCE, COL_ID_ANNONCE + " = " + id, null);
        close();
    }

}

