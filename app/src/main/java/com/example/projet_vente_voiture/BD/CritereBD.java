package com.example.projet_vente_voiture.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.Critere;

import java.util.ArrayList;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_CRITERE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_CRITERE;

public class CritereBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private MaBaseSQLite maBaseSQLite;

    public CritereBD(Context context) {
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

    public Critere getCritereById(int critere) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_CRITERE +" WHERE "+ COL_ID_CRITERE +" = "+ critere,null);
        List<Critere> list = cursorToCriteres(c);
        if(list==null){
            return null;
        }
        close();
        return list.get(0);
    }

    public List<Critere> getAllCritere() {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_CRITERE ,null);
        List<Critere> list = cursorToCriteres(c);
        if(list==null){
            return null;
        }
        close();
        return list;
    }

    private List<Critere> cursorToCriteres(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<Critere> res = new ArrayList<>();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(0);
            String nom = c.getString(1);
            int type = c.getInt(2);

            res.add(new Critere(id,nom,type));
            c.moveToNext();
        }
        c.close();
        return res;
    }

}

