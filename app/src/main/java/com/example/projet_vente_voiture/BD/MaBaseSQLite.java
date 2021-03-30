package com.example.projet_vente_voiture.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLite extends SQLiteOpenHelper {

    static final String TABLE_UTILISATEUR ="table_utilisateur";
    static final String TABLE_ANNONCE ="table_annonce";

    static final String COL_ID_UTILISATEUR ="id_utilisateur ";
    static final String COL_PRENOM_UTILISATEUR ="prenom_utilisateur";
    static final String COL_NOM_UTILISATEUR ="nom_utilisateur";
    static final String COL_MAIL_UTILISATEUR ="mail_utilisateur";
    static final String COL_MDP_UTILISATEUR ="mdp_utilisateur";
    static final String COL_PROFESSIONNEL_UTILISATEUR ="professionnel_utilisateur";

    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE "+TABLE_UTILISATEUR+"("
                    + COL_ID_UTILISATEUR +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_PRENOM_UTILISATEUR+" TEXT, "
                    + COL_NOM_UTILISATEUR +" TEXT,"
                    + COL_MAIL_UTILISATEUR +" TEXT,"
                    + COL_MDP_UTILISATEUR +" TEXT,"
                    + COL_PROFESSIONNEL_UTILISATEUR +" BOOLEAN,"
                    +");";

    private static final String INSERT_UTILISATEUR =
            "INSERT INTO "+TABLE_UTILISATEUR +"("+COL_PRENOM_UTILISATEUR +","+COL_NOM_UTILISATEUR+","+COL_MAIL_UTILISATEUR+","+COL_MDP_UTILISATEUR+","+COL_PROFESSIONNEL_UTILISATEUR+")"
                    +"VALUES('Tessy','Minodier','tessy.minodier@etu.umontpellier.fr','123456',TRUE);";

    MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(INSERT_UTILISATEUR);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
