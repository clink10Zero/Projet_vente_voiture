package com.example.projet_vente_voiture.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.Annonce;

import java.util.ArrayList;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_DATE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_DESCRITPION_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_LIEU_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_LOCATION_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_LOCATION_TEMPS_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PRIX_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PROMOTION_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_TITRE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_AUTEUR_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_VU_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.YES;

public class AnnonceBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private final MaBaseSQLite maBaseSQLite;

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
        values.put(COL_AUTEUR_ANNONCE, annonce.getId_auteur());
        values.put(COL_TITRE_ANNONCE, annonce.getTitre());
        values.put(COL_DESCRITPION_ANNONCE, annonce.getDescritpion());
        values.put(COL_LIEU_ANNONCE, annonce.getLieu());
        values.put(COL_PRIX_ANNONCE, annonce.getPrix());
        values.put(COL_DATE_ANNONCE, annonce.getDate());
        values.put(COL_VU_ANNONCE, annonce.getVu());
        values.put(COL_PROMOTION_ANNONCE, annonce.getPromotion());
        values.put(COL_LOCATION_ANNONCE, annonce.getLocation());
        values.put(COL_LOCATION_TEMPS_ANNONCE, annonce.getTemps());
        int id = (int) bd.insert(TABLE_ANNONCE,null,values);
        annonce.setId(id);
        close();
    }

    public void updateAnnonce(int id, Annonce annonce) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_AUTEUR_ANNONCE, annonce.getId_auteur());
        values.put(COL_TITRE_ANNONCE, annonce.getTitre());
        values.put(COL_DESCRITPION_ANNONCE, annonce.getDescritpion());
        values.put(COL_LIEU_ANNONCE, annonce.getLieu());
        values.put(COL_PRIX_ANNONCE, annonce.getPrix());
        values.put(COL_DATE_ANNONCE, annonce.getDate());
        values.put(COL_VU_ANNONCE, annonce.getVu());
        values.put(COL_PROMOTION_ANNONCE, annonce.getPromotion());
        values.put(COL_LOCATION_ANNONCE, annonce.getLocation());
        values.put(COL_LOCATION_TEMPS_ANNONCE, annonce.getTemps());
        bd.update(TABLE_ANNONCE, values, COL_ID_ANNONCE + " = " + id, null);
        annonce.setId(id);
        close();
    }

    public void removeAnnonceWithID(int id) {
        open();
        bd.delete(TABLE_ANNONCE, COL_ID_ANNONCE + " = " + id, null);
        close();
    }

    public Annonce getAnnonceById(int annonce) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE +" WHERE "+ COL_ID_ANNONCE +" = "+ annonce,null);
        List<Annonce> list = cursorToAnnonces(c);
        if(list==null){
            return null;
        }
        close();
        return list.get(0);
    }

    public List<Annonce> getAllAnnonces() {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE + " ORDER BY " + COL_DATE_ANNONCE + " DESC ",null);
        List<Annonce> list = cursorToAnnonces(c);
        close();
        return list;
    }

    public List<Annonce> getAnnoncesByUserId(int userId) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE+" WHERE "+ COL_AUTEUR_ANNONCE +" = "+ userId + " ORDER BY " + COL_DATE_ANNONCE + " DESC ",null);
        List<Annonce> list = cursorToAnnonces(c);
        close();
        return list;
    }

    public List<Annonce> getAnnoncesByUserIdAndIfIsLocation(int userId, int location) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE+" WHERE "+ COL_AUTEUR_ANNONCE +" = "+ userId + " AND " + COL_LOCATION_ANNONCE + " = " + location + " ORDER BY " + COL_DATE_ANNONCE + " DESC ",null);
        List<Annonce> list = cursorToAnnonces(c);
        close();
        return list;
    }

    public List<Annonce> getPromotedAnnoncesByUserId(int userId) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE+" WHERE "+ COL_AUTEUR_ANNONCE +" = "+ userId + " AND " + COL_PROMOTION_ANNONCE + " = " + YES + " ORDER BY " + COL_DATE_ANNONCE + " DESC ",null);
        List<Annonce> list = cursorToAnnonces(c);
        close();
        return list;
    }

    public List<Annonce> getPromotedAnnoncesByUserIdAndIfIsLocation(int userId, int location) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_ANNONCE+" WHERE "+ COL_AUTEUR_ANNONCE +" = "+ userId + " AND " + COL_LOCATION_ANNONCE + " = " + location + " AND " + COL_PROMOTION_ANNONCE + " = " + YES + " ORDER BY " + COL_DATE_ANNONCE + " DESC ",null);
        List<Annonce> list = cursorToAnnonces(c);
        close();
        return list;
    }

    private List<Annonce> cursorToAnnonces(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<Annonce> res = new ArrayList<>();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(0);
            int id_utilisateur = c.getInt(1);
            String titre = c.getString(2);
            String description = c.getString(3);
            String lieu = c.getString(4);
            int prix = c.getInt(5);
            String date = c.getString(6);
            int vu = c.getInt(7);
            int promotion = c.getInt(8);
            int location = c.getInt(9);
            int temps = c.getInt(10);

            res.add(new Annonce(id,id_utilisateur,titre,description,lieu,prix,date,vu,promotion,location,temps));
            c.moveToNext();
        }
        c.close();
        return res;
    }

}

