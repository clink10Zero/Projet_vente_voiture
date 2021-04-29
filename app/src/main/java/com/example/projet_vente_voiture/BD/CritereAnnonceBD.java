package com.example.projet_vente_voiture.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.CritereAnnonce;

import java.util.ArrayList;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ANNONCE_CRITERE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_AUTEUR_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_CRITERE_CRITERE_ANONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_CRITERE_ANONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_VALEUR_CRITERE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_CRITERE_ANNONCE;

public class CritereAnnonceBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private MaBaseSQLite maBaseSQLite;

    public CritereAnnonceBD(Context context) {
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

    public void insertCritereAnnonce(CritereAnnonce critereAnnonce) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_CRITERE_CRITERE_ANONCE, critereAnnonce.getId_critere());
        values.put(COL_ANNONCE_CRITERE_ANNONCE, critereAnnonce.getId_annonce());
        values.put(COL_VALEUR_CRITERE_ANNONCE, critereAnnonce.getValeur());

        int id = (int) bd.insert(TABLE_CRITERE_ANNONCE,null,values);
        critereAnnonce.setId(id);
        close();
    }

    public void updateCritereAnnonce(int id, CritereAnnonce critereAnnonce) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_CRITERE_CRITERE_ANONCE, critereAnnonce.getId_critere());
        values.put(COL_ANNONCE_CRITERE_ANNONCE, critereAnnonce.getId_annonce());
        values.put(COL_VALEUR_CRITERE_ANNONCE, critereAnnonce.getValeur());

        bd.update(TABLE_CRITERE_ANNONCE, values, COL_ID_CRITERE_ANONCE + " = " + id, null);
    }

    public List<CritereAnnonce> getCritereAnnonceByAnnonceId(int annonce) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_CRITERE_ANNONCE +" WHERE "+ COL_ANNONCE_CRITERE_ANNONCE +" = "+ annonce,null);
        List<CritereAnnonce> list = cursorToCritereAnnonces(c);
        if(list==null){
            return null;
        }
        close();
        return list;
    }

    public CritereAnnonce getCritereAnnonceByAnnonceAndCritereId(int annonce,int critere) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_CRITERE_ANNONCE +" WHERE "+ COL_ANNONCE_CRITERE_ANNONCE +" = "+ annonce + " AND " + COL_CRITERE_CRITERE_ANONCE + " = " + critere,null);
        List<CritereAnnonce> list = cursorToCritereAnnonces(c);
        if(list==null){
            return null;
        }
        close();
        return list.get(0);
    }

    private List<CritereAnnonce> cursorToCritereAnnonces(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<CritereAnnonce> res = new ArrayList<>();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(0);
            int id_critere = c.getInt(1);
            int id_annonce = c.getInt(2);
            String valeur = c.getString(3);


            res.add(new CritereAnnonce(id,id_critere,id_annonce,valeur));
            c.moveToNext();
        }
        c.close();
        return res;
    }

}

