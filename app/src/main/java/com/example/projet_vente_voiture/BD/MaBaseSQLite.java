package com.example.projet_vente_voiture.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.projet_vente_voiture.Object.Utilisateur.PARTICULIER;
import static com.example.projet_vente_voiture.Object.Utilisateur.PROFESSIONEL;

public class MaBaseSQLite extends SQLiteOpenHelper {

    public static final String NOM_BD="base.bd";

    public static final int NO = 0;
    public static final int YES = 1;

    public static final int HEURE =0;
    public static final int JOUR =1;
    public static final int SEMAINE =2;
    public static final int MOIS =3;

    static final String TABLE_UTILISATEUR ="table_utilisateur";
    public static final String TABLE_ANNONCE ="table_annonce";
    static final String TABLE_PHOTO ="table_photo";
    static final String TABLE_CRITERE ="table_critere";
    static final String TABLE_VALEUR_CRITERE ="table_valeur_critere";
    public static final String TABLE_CRITERE_ANNONCE ="table_critere_annonce";
    static final String TABLE_ANNONCE_SAUVEGARDE = "table_annonce_sauvegarde";

    static final String COL_ID_UTILISATEUR ="id_utilisateur ";
    static final String COL_PRENOM_UTILISATEUR ="prenom_utilisateur";
    static final String COL_NOM_UTILISATEUR ="nom_utilisateur";
    static final String COL_MAIL_UTILISATEUR ="mail_utilisateur";
    static final String COL_MDP_UTILISATEUR ="mdp_utilisateur";
    static final String COL_PROFESSIONNEL_UTILISATEUR ="professionnel_utilisateur";
    static final String COL_ABONNEMENT_UTILISATEUR ="abonnement_utilisateur";

    public static final String COL_ID_ANNONCE ="id_annonce";
    static final String COL_AUTEUR_ANNONCE ="id_auteur";
    public static final String COL_TITRE_ANNONCE ="titre_annonce";
    public static final String COL_DESCRITPION_ANNONCE ="description_annonce";
    public static final String COL_LIEU_ANNONCE ="lieu_annonce";
    public static final String COL_PRIX_ANNONCE ="prix_annonce";
    static final String COL_DATE_ANNONCE ="date_annonce";
    static final String COL_VU_ANNONCE ="nombre_vues_annonce";
    static final String COL_PROMOTION_ANNONCE ="promotion_annonce";
    public static final String COL_LOCATION_ANNONCE ="location_annonce"; //bool
    static final String COL_LOCATION_TEMPS_ANNONCE ="location_temps_annonce"; //JOUR/SEMAINE/HEURE/MOIS pour le prix

    static final String COL_ID_PHOTO="id_photo";
    static final String COL_ANNONCE_PHOTO ="id_annonce";
    static final String COL_PATH_PHOTO ="path_photo";

    static final String COL_ID_CRITERE="id_critere";
    static final String COL_NOM_CRITERE ="nom_critere";
    static final String COL_TYPE_CRITERE ="type_critere";

    public static final int CRITERE_PREDEF =0;
    static final int CRITERE_NUM =1;

    static final String COL_ID_VALEUR_CRITERE="id_valeur_critere";
    static final String COL_CRITERE_VALEUR_CRITERE="id_critere";
    static final String COL_VALEUR_VALEUR_CRITERE="valeur_valeur_critere";

    static final String COL_ID_CRITERE_ANONCE="id_critere_annonce";
    public static final String COL_CRITERE_CRITERE_ANONCE="id_critere";
    public static final String COL_ANNONCE_CRITERE_ANNONCE="id_annonce";
    public static final String COL_VALEUR_CRITERE_ANNONCE="valeur_critere_annonce";

    static final String COL_ID_ANNONCE_SAUVEGARDE="id_annonce_sauvegarde";
    static final String COL_UTILISATEUR_ANNONCE_SAUVEGARDE="id_utilisateur";
    static final String COL_ANNONCE_ANNONCE_SAUVEGARDE="id_annonce";
 
    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE "+TABLE_UTILISATEUR+"("
                    + COL_ID_UTILISATEUR +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_PRENOM_UTILISATEUR+" TEXT, "
                    + COL_NOM_UTILISATEUR +" TEXT,"
                    + COL_MAIL_UTILISATEUR +" TEXT UNIQUE,"
                    + COL_MDP_UTILISATEUR +" TEXT NOT NULL,"
                    + COL_PROFESSIONNEL_UTILISATEUR +" INTEGER NOT NULL CHECK (" + COL_PROFESSIONNEL_UTILISATEUR + " IN( " + PROFESSIONEL + "," + PARTICULIER +")),"
                    + COL_ABONNEMENT_UTILISATEUR +" INTEGER NOT NULL CHECK (" + COL_ABONNEMENT_UTILISATEUR + " IN( " + NO + "," + YES +"))"
                    +");";

    private static final String CREATE_TABLE_ANNONCE =
            "CREATE TABLE "+TABLE_ANNONCE+"("
                    + COL_ID_ANNONCE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_AUTEUR_ANNONCE + " INTEGER NOT NULL, "
                    + COL_TITRE_ANNONCE + " TEXT, "
                    + COL_DESCRITPION_ANNONCE + " TEXT, "
                    + COL_LIEU_ANNONCE + " TEXT NOT NULL, " //pas sur
                    + COL_PRIX_ANNONCE + " INTEGER NOT NULL, "
                    + COL_DATE_ANNONCE + " TEXT NOT NULL, " //TODO y faire plus plus joli apr??s
                    + COL_VU_ANNONCE + " INTEGER NOT NULL,"
                    + COL_PROMOTION_ANNONCE + " INTEGER NOT NULL CHECK (" + COL_PROMOTION_ANNONCE + " IN( " + NO + "," + YES +")),"
                    + COL_LOCATION_ANNONCE + " INTEGER NOT NULL CHECK (" + COL_LOCATION_ANNONCE + " IN( " + NO + "," + YES +")),"
                    + COL_LOCATION_TEMPS_ANNONCE + " INTEGER NOT NULL CHECK (" + COL_LOCATION_TEMPS_ANNONCE + " IN( " + HEURE + "," + JOUR + "," + SEMAINE + "," + MOIS +")),"
                    + "FOREIGN KEY("+ COL_AUTEUR_ANNONCE +")REFERENCES "+ TABLE_UTILISATEUR+"("+COL_ID_UTILISATEUR+") ON DELETE CASCADE ON UPDATE CASCADE"
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

    private static final String CREATE_TABLE_CRITERE_ANNONCE =
            "CREATE TABLE "+TABLE_CRITERE_ANNONCE+" ("
                    + COL_ID_CRITERE_ANONCE +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_CRITERE_CRITERE_ANONCE + " INTEGER NOT NULL,"
                    + COL_ANNONCE_CRITERE_ANNONCE + " INTEGER NOT NULL,"
                    + COL_VALEUR_CRITERE_ANNONCE + " TEXT,"
                    + "FOREIGN KEY ( " + COL_ANNONCE_CRITERE_ANNONCE +" ) REFERENCES " + TABLE_ANNONCE + "(" +COL_ID_ANNONCE+ ") ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY ( " + COL_CRITERE_CRITERE_ANONCE +" ) REFERENCES " + TABLE_CRITERE + "(" +COL_ID_CRITERE+ ") ON DELETE CASCADE ON UPDATE CASCADE"
                    +");";

    private static final String CREATE_TABLE_ANNONCE_SAUVEGARDE =
            "CREATE TABLE "+TABLE_ANNONCE_SAUVEGARDE+" ("
                    + COL_ID_ANNONCE_SAUVEGARDE +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_UTILISATEUR_ANNONCE_SAUVEGARDE + " INTEGER NOT NULL,"
                    + COL_ANNONCE_ANNONCE_SAUVEGARDE + " INTEGER NOT NULL,"
                    + "FOREIGN KEY ( " + COL_ANNONCE_ANNONCE_SAUVEGARDE +" ) REFERENCES " + TABLE_ANNONCE + "(" +COL_ID_ANNONCE+ ") ON DELETE CASCADE ON UPDATE CASCADE,"
                    + "FOREIGN KEY ( " + COL_UTILISATEUR_ANNONCE_SAUVEGARDE +" ) REFERENCES " + TABLE_UTILISATEUR + "(" +COL_ID_UTILISATEUR+ ") ON DELETE CASCADE ON UPDATE CASCADE"
                    +");";

    private static final String INSERT_UTILISATEUR =
            "INSERT INTO "+TABLE_UTILISATEUR +"("+COL_PRENOM_UTILISATEUR +","+COL_NOM_UTILISATEUR+","+COL_MAIL_UTILISATEUR+","+COL_MDP_UTILISATEUR+","+COL_PROFESSIONNEL_UTILISATEUR+","+COL_ABONNEMENT_UTILISATEUR+")"
                    +"VALUES('Tessy','Minodier','tessy.minodier@etu.umontpellier.fr','123456',"+PROFESSIONEL+","+YES+");";

    //TODO continuer les insert de critere
    private static final String INSERT_CRITERE =
            "INSERT INTO "+TABLE_CRITERE +"("+COL_NOM_CRITERE +","+COL_TYPE_CRITERE+")"
                /*1*/ +"VALUES('Marque',"+CRITERE_PREDEF +"),"
                /*2*/ + "('Ann??e'," + CRITERE_NUM + ");";

    private static final String INSERT_VALEUR_CRITERE =
            "INSERT INTO "+TABLE_VALEUR_CRITERE +"("+COL_CRITERE_VALEUR_CRITERE +","+COL_VALEUR_VALEUR_CRITERE+")"
                    +"VALUES(1,'Peugeot'),"
                    +"(1,'Renault'),"
                    +"(1,'Ford'),"
                    +"(1,'Citroen');";


    private static final String INSERT_ANNONCE =
            "INSERT INTO "+TABLE_ANNONCE +"("+ COL_AUTEUR_ANNONCE +","+COL_TITRE_ANNONCE +","+ COL_DESCRITPION_ANNONCE +","+COL_LIEU_ANNONCE  +","+COL_PRIX_ANNONCE+","+COL_DATE_ANNONCE+","+COL_VU_ANNONCE+","+COL_PROMOTION_ANNONCE+","+COL_LOCATION_ANNONCE+","+COL_LOCATION_TEMPS_ANNONCE+")"
                /*1*/ +"VALUES(1,'Vends ma R5','Je vends la R5 de ma grand m??re je n en ai plus besoin maintenant que j habite en ville', 'Annonay', 2000,'01/04/2021',0,"+NO+","+NO+","+HEURE+"),"
                /*2*/ +"(1,'Ka noir', 'moteur cass?? prix cass??', 'Saint-Vallier',200,'01/04/2021',0,"+NO+","+NO+","+HEURE+"),"
                /*3*/ +"(1,'Mondeo break 2007 gris', 'Les enfants sont grands je n ai plus besoin d un break. \n Voiture en tr??s bon ??tat', 'Annonay',3000,'01/04/2021',0,"+YES+","+NO+","+HEURE+");";

    private static final String INSERT_CRITERE_ANNONCE =
            "INSERT INTO "+TABLE_CRITERE_ANNONCE +"( "+ COL_CRITERE_CRITERE_ANONCE +","+COL_ANNONCE_CRITERE_ANNONCE +","+COL_VALEUR_CRITERE_ANNONCE+" )"
                    +"VALUES"
                    +"(1,1, 'Renault'),"
                    +"(1,2, 'Ford'),"
                    +"(1,3, 'Ford');";

    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_ANNONCE);
        db.execSQL(CREATE_TABLE_PHOTO);
        db.execSQL(CREATE_TABLE_CRITERE);
        db.execSQL(CREATE_TABLE_VALEUR_CRITERE);
        db.execSQL(CREATE_TABLE_CRITERE_ANNONCE);
        db.execSQL(CREATE_TABLE_ANNONCE_SAUVEGARDE);

        db.execSQL(INSERT_UTILISATEUR);
        db.execSQL(INSERT_CRITERE);
        db.execSQL(INSERT_VALEUR_CRITERE);
        db.execSQL(INSERT_ANNONCE);
        db.execSQL(INSERT_CRITERE_ANNONCE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
