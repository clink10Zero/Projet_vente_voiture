package com.example.projet_vente_voiture.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_vente_voiture.Object.Photo;

import java.util.ArrayList;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ANNONCE_PHOTO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_PHOTO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PATH_PHOTO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_PHOTO;

public class PhotoBD {
    private static final int VERSION_BD = 1;

    private SQLiteDatabase bd;
    private MaBaseSQLite maBaseSQLite;

    public PhotoBD(Context context) {
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

    public void insertPhoto(Photo photo) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_ANNONCE_PHOTO, photo.getId_annonce());
        values.put(COL_PATH_PHOTO, photo.getChemin());
        int id = (int) bd.insert(TABLE_PHOTO,null,values);
        photo.setId(id);
        close();
    }

    public void updatePhoto(int id, Photo photo) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_ANNONCE_PHOTO, photo.getId_annonce());
        values.put(COL_PATH_PHOTO, photo.getChemin());
        bd.update(TABLE_PHOTO, values, COL_ID_PHOTO + " = " + id, null);
    }

    public void removePhotoWithID(int id) {
        open();
        bd.delete(TABLE_PHOTO, COL_ID_PHOTO + " = " + id, null);
        close();
    }

    public Photo getPhotoById(int annonce) {
        open();
        Cursor c = bd.rawQuery("SELECT * FROM "+ TABLE_PHOTO +" WHERE "+ COL_ID_PHOTO +" = "+ annonce,null);
        List<Photo> list = cursorToPhotos(c);
        if(list==null){
            return null;
        }
        close();
        return list.get(0);
    }

    private List<Photo> cursorToPhotos(Cursor c) {
        if (c.getCount() == 0)
            return null;

        List<Photo> res = new ArrayList<>();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(0);
            int id_annonce = c.getInt(1);
            String chemin = c.getString(2);

            res.add(new Photo(id,id_annonce,chemin));
            c.moveToNext();
        }
        c.close();
        return res;
    }

}

