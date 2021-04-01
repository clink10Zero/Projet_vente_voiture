package com.example.projet_vente_voiture.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.projet_vente_voiture.Object.Utilisateur.PARTICULIER;
import static com.example.projet_vente_voiture.Object.Utilisateur.PROFESSIONEL;

public class MaBaseSQLite extends SQLiteOpenHelper {

    static final String NOM_BD="base.bd";

    static final String TABLE_UTILISATEUR ="table_utilisateur";
    static final String TABLE_ANNONCE ="table_annonce";
    static final String TABLE_PHOTO ="table_photo";
    static final String TABLE_CRITERE ="table_critere";
    static final String TABLE_VALEUR_CRITERE ="table_valeur_critere";

    static final String COL_ID_UTILISATEUR ="id_utilisateur ";
    static final String COL_PRENOM_UTILISATEUR ="prenom_utilisateur";
    static final String COL_NOM_UTILISATEUR ="nom_utilisateur";
    static final String COL_MAIL_UTILISATEUR ="mail_utilisateur";
    static final String COL_MDP_UTILISATEUR ="mdp_utilisateur";
    static final String COL_PROFESSIONNEL_UTILISATEUR ="professionnel_utilisateur";

    static final String COL_ID_ANNONCE ="id_annonce";
    static final String COL_UTILISATEUR_ANNONCE ="id_utilisateur";
    static final String COL_TITRE_ANNONCE ="titre_annonce";
    static final String COL_DESCCRITPION_ANNONCE ="description_annonce";
    static final String COL_LIEU_ANNONCE ="lieu_annonce";
    static final String COL_PRIX_ANNONCE ="prix_annonce";
    //TODO gérer les locations
    static final String COL_LOCATION_ANNONCE ="location_annonce"; //bool
    static final String COL_LOCATION_DUREE_ANNONCE ="location_duree_annonce"; //JOUR/SEMAINE/HEURE/MOIS pour le prix
    static final String COL_LOCATION_DEBUT_ANNONCE ="location_debut_annonce";
    static final String COL_LOCATION_FIN_ANNONCE ="location_fin_annonce";

    static final String COL_ID_PHOTO="id_photo";
    static final String COL_ANNONCE_PHOTO ="id_annonce";
    static final String COL_PATH_PHOTO ="path_photo";

    static final String COL_ID_CRITERE="id_critere";
    static final String COL_NOM_CRITERE ="nom_critere";
    static final String COL_TYPE_CRITERE ="type_critere";

    static final int CRITERE_PREDEF =0;
    static final int CRITERE_NUM =1;

    static final String COL_ID_VALEUR_CRITERE="id_valeur_critere";
    static final String COL_CRITERE_VALEUR_CRITERE="id_critere";
    static final String COL_VALEUR_VALEUR_CRITERE="valeur_valeur_critere";

    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE "+TABLE_UTILISATEUR+"("
                    + COL_ID_UTILISATEUR +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_PRENOM_UTILISATEUR+" TEXT, "
                    + COL_NOM_UTILISATEUR +" TEXT,"
                    + COL_MAIL_UTILISATEUR +" TEXT UNIQUE,"
                    + COL_MDP_UTILISATEUR +" TEXT NOT NULL,"
                    + COL_PROFESSIONNEL_UTILISATEUR +" INTEGER NOT NULL CHECK (" + COL_PROFESSIONNEL_UTILISATEUR + " IN( " + PROFESSIONEL + "," + PARTICULIER +"))"
                    +");";

    private static final String CREATE_TABLE_ANNONCE =
            "CREATE TABLE "+TABLE_ANNONCE+"("
                    + COL_ID_ANNONCE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_UTILISATEUR_ANNONCE + " INTEGER NOT NULL, "
                    + COL_TITRE_ANNONCE + " TEXT, "
                    + COL_DESCCRITPION_ANNONCE + " TEXT, "
                    + COL_LIEU_ANNONCE + " TEXT NOT NULL, " //pas sur
                    + COL_PRIX_ANNONCE + " INTEGER NOT NULL, "
                    + "FOREIGN KEY("+COL_UTILISATEUR_ANNONCE+")REFERENCES "+ TABLE_UTILISATEUR+"("+COL_ID_UTILISATEUR+") ON DELETE CASCADE ON UPDATE CASCADE"
                    +");";

    private static final String CREATE_TABLE_PHOTO =
            "CREATE TABLE "+TABLE_PHOTO+" ("
                    + COL_ID_PHOTO +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_ANNONCE_PHOTO +" INTEGER NOT NULL, "
                    + COL_PATH_PHOTO +" TEXT NOT NULL,"
                    + "FOREIGN KEY("+ COL_ANNONCE_PHOTO +")REFERENCES "+ TABLE_ANNONCE+"("+COL_ID_ANNONCE+") ON DELETE CASCADE ON UPDATE CASCADE"
                    +");";

    private static final String CREATE_TABLE_CRITERE =
            "CREATE TABLE "+TABLE_CRITERE+" ("
                    + COL_ID_CRITERE +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_NOM_CRITERE +" TEXT NOT NULL, "
                    + COL_TYPE_CRITERE +" INTEGER NOT NULL CHECK( " + COL_TYPE_CRITERE + " IN( " + CRITERE_PREDEF + " , " + CRITERE_NUM + " )) "
                    +");";

    private static final String CREATE_TABLE_VALEUR_CRITERE =
            "CREATE TABLE "+TABLE_VALEUR_CRITERE+" ("
                    + COL_ID_VALEUR_CRITERE +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_CRITERE_VALEUR_CRITERE +" TEXT NOT NULL, "
                    + COL_VALEUR_VALEUR_CRITERE +" TEXT NOT NULL, "
                    + "FOREIGN KEY("+ COL_CRITERE_VALEUR_CRITERE +")REFERENCES "+ TABLE_CRITERE+"("+COL_ID_CRITERE+") ON DELETE CASCADE ON UPDATE CASCADE"
                    +");";

    private static final String INSERT_UTILISATEUR =
            "INSERT INTO "+TABLE_UTILISATEUR +"("+COL_PRENOM_UTILISATEUR +","+COL_NOM_UTILISATEUR+","+COL_MAIL_UTILISATEUR+","+COL_MDP_UTILISATEUR+","+COL_PROFESSIONNEL_UTILISATEUR+")"
                    +"VALUES('Tessy','Minodier','tessy.minodier@etu.umontpellier.fr','123456',"+PROFESSIONEL+");";

    //TODO continuer les insert de critere
    private static final String INSERT_CRITERE =
            "INSERT INTO "+TABLE_CRITERE +"("+COL_NOM_CRITERE +","+COL_TYPE_CRITERE+")"
                /*1*/ +"VALUES('Marque',"+CRITERE_PREDEF +"),"
                /*2*/ + "('Prix'," + CRITERE_NUM + ");";

    private static final String INSERT_VALEUR_CRITERE =
            "INSERT INTO "+TABLE_VALEUR_CRITERE +"("+COL_CRITERE_VALEUR_CRITERE +","+COL_VALEUR_VALEUR_CRITERE+")"
                    +"VALUES(1,'Peugeot'),"
                    +"(1,'Renault'),"
                    +"(1,'Citroen');";


    private static final String INSERT_ANNONCE =
            "INSERT INTO "+TABLE_ANNONCE +"("+COL_UTILISATEUR_ANNONCE  +","+COL_TITRE_ANNONCE +","+COL_DESCCRITPION_ANNONCE   +","+COL_LIEU_ANNONCE  +","+COL_PRIX_ANNONCE+")"
                    +"VALUES(1,'Vends ma R5','Je vends la R5 de ma rand mère je n en ai plus besoin maintenant que j habite en ville', 'Annonay', 2000),"
                    +"(1,'Ka noir', 'moteur cassé prix cassé', 'Saint-Vallier',200),"
                    +"(1,'Mondeo break 2007 gris', 'les enfants sont grand j ai plus beosin d un bbreak \n voiture en très boon état', 'Annonay',3000);";


    MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_ANNONCE);
        db.execSQL(CREATE_TABLE_PHOTO);
        db.execSQL(CREATE_TABLE_CRITERE);
        db.execSQL(CREATE_TABLE_VALEUR_CRITERE);

        db.execSQL(INSERT_UTILISATEUR);
        db.execSQL(INSERT_CRITERE);
        db.execSQL(INSERT_VALEUR_CRITERE);
        db.execSQL(INSERT_ANNONCE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
