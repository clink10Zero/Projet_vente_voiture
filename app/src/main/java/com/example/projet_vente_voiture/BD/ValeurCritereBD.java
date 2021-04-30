package com.example.projet_vente_voiture.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.ValeurCritere;

import java.util.ArrayList;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_CRITERE_VALEUR_CRITERE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_VALEUR_CRITERE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_VALEUR_VALEUR_CRITERE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_VALEUR_CRITERE;

public class ValeurCritereBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private MaBaseSQLite maBaseSQLite;

    public ValeurCritereBD(Context context) {
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

    public void insertValeurCritere(ValeurCritere valeurCritere) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_CRITERE_VALEUR_CRITERE, valeurCritere.getCritere());
        values.put(COL_VALEUR_VALEUR_CRITERE, valeurCritere.getValeur());
        int id = (int) bd.insert(TABLE_VALEUR_CRITERE,null,values);
        valeurCritere.setId(id);
        close();
    }

    public List<ValeurCritere> getValeurCritereByCritere(int critere) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_VALEUR_CRITERE +" WHERE "+ COL_CRITERE_VALEUR_CRITERE +" = "+ critere,null);
        List<ValeurCritere> list = cursorToValeurCriteres(c);
        if(list==null){
            return null;
        }
        close();
        return list;
    }

    public ValeurCritere getValeurCritereById(int valeurCritere) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_VALEUR_CRITERE +" WHERE "+ COL_ID_VALEUR_CRITERE +" = "+ valeurCritere,null);
        List<ValeurCritere> list = cursorToValeurCriteres(c);
        if(list==null){
            return null;
        }
        close();
        return list.get(0);
    }

    private List<ValeurCritere> cursorToValeurCriteres(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<ValeurCritere> res = new ArrayList<>();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(0);
            int id_critere = c.getInt(1);
            String valeur = c.getString(2);

            res.add(new ValeurCritere(id,id_critere,valeur));
            c.moveToNext();
        }
        c.close();
        return res;
    }

}

