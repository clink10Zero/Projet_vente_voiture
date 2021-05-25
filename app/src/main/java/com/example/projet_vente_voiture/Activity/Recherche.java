package com.example.projet_vente_voiture.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.CritereBD;
import com.example.projet_vente_voiture.BD.MaBaseSQLite;
import com.example.projet_vente_voiture.BD.ValeurCritereBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.View.AnnonceView;
import com.example.projet_vente_voiture.Object.Critere;
import com.example.projet_vente_voiture.Object.ValeurCritere;
import com.example.projet_vente_voiture.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ANNONCE_CRITERE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_CRITERE_CRITERE_ANONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_DESCRITPION_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_ID_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_LIEU_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_LOCATION_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_PRIX_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_TITRE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.COL_VALEUR_CRITERE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.CRITERE_PREDEF;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NOM_BD;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.TABLE_CRITERE_ANNONCE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.YES;

public class Recherche extends General {

    private LinearLayout dynamic;
    private int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.poule0);
        setSupportActionBar(findViewById(R.id.toolbar));

        AnnonceBD ABD = new AnnonceBD(getApplicationContext());

        LinearLayout ll_critere = findViewById(R.id.dynamic_linear_layout_criteres_recherche);
        LinearLayout ligne = new LinearLayout(this);
        ligne.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv_name_prix = new TextView(this);
        tv_name_prix.setText("Prix");
        ligne.addView(tv_name_prix);

        LinearLayout col_inf_prix = new LinearLayout(this);
        col_inf_prix.setOrientation(LinearLayout.HORIZONTAL);
        TextView tv_inf_prix = new TextView(this);
        tv_inf_prix.setText("Borne inf.");
        col_inf_prix.addView(tv_inf_prix);
        EditText et_inf_prix = new EditText(this);
        col_inf_prix.addView(et_inf_prix);

        LinearLayout col_sup_prix = new LinearLayout(this);
        col_sup_prix.setOrientation(LinearLayout.HORIZONTAL);
        TextView tv_sup_prix = new TextView(this);
        tv_sup_prix.setText("Borne sup.");
        col_sup_prix.addView(tv_sup_prix);
        EditText et_sup_prix = new EditText(this);
        col_sup_prix.addView(et_sup_prix);

        ligne.addView(col_inf_prix);
        ligne.addView(col_sup_prix);

        ll_critere.addView(ligne);

        List<Critere> critere_et_list = new ArrayList<>();
        List<Critere> critere_spinner_list = new ArrayList<>();
        List<EditText> et_List = new ArrayList<>();
        List<Spinner> spinner_List = new ArrayList<>();
        CritereBD CBD = new CritereBD(this);
        for(Critere c : CBD.getAllCritere()){
            ligne = new LinearLayout(this);
            ligne.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv_name = new TextView(this);
            tv_name.setText(c.getNom());
            ligne.addView(tv_name);

            if(c.getType()==CRITERE_PREDEF){
                Spinner spinner = new Spinner(this);

                List<String> spinnerElement = new ArrayList<>();

                ValeurCritereBD VCBD = new ValeurCritereBD(this);
                ArrayList<String> list = new ArrayList<>();
                spinnerElement.add("");
                List<ValeurCritere> vcs = VCBD.getValeurCritereByCritere(c.getId());
                if (vcs!=null){
                    for(ValeurCritere valeurCritere :  vcs){
                        list.add(valeurCritere.getValeur());
                    }
                    spinnerElement.addAll(list);
                }

                ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.spinner_item_selected, spinnerElement);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinner.setAdapter(adapter);
                ligne.addView(spinner);

                spinner_List.add(spinner);
                critere_spinner_list.add(c);
            }
            else{
                LinearLayout col_inf = new LinearLayout(this);
                col_inf.setOrientation(LinearLayout.HORIZONTAL);
                TextView tv_inf = new TextView(this);
                tv_inf.setText("Borne inf.");
                col_inf.addView(tv_inf);
                EditText et_inf = new EditText(this);
                col_inf.addView(et_inf);
                et_List.add(et_inf);

                LinearLayout col_sup = new LinearLayout(this);
                col_sup.setOrientation(LinearLayout.HORIZONTAL);
                TextView tv_sup = new TextView(this);
                tv_sup.setText("Borne sup.");
                col_sup.addView(tv_sup);
                EditText et_sup = new EditText(this);
                col_sup.addView(et_sup);
                et_List.add(et_sup);

                ligne.addView(col_inf);
                ligne.addView(col_sup);
                critere_et_list.add(c);
                critere_et_list.add(c);
            }
            ll_critere.addView(ligne);
        }

        SwitchCompat switch_vente=findViewById(R.id.switch_vente_recherche);
        SwitchCompat switch_loc=findViewById(R.id.switch_location_recherche);

        if(switch_vente.isChecked()){
            location=NO;
            switch_loc.setChecked(false);
        }

        switch_vente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    location = NO;
                    if (switch_loc.isChecked()) {
                        switch_loc.setChecked(false);
                    }
                } else {
                    location = YES;
                    if (!switch_loc.isChecked()) {
                        switch_loc.setChecked(true);
                    }
                }
            }
        });

        switch_loc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    location=YES;
                    if(switch_vente.isChecked()) {
                        switch_vente.setChecked(false);
                    }
                }
                else{
                    location=NO;
                    if(!switch_vente.isChecked()){
                        switch_vente.setChecked(true);
                    }
                }
            }
        });


        Button btn_rechercher = findViewById(R.id.button_recherche_recherche);
        btn_rechercher.setOnClickListener(v -> {
            String request = "SELECT " + COL_ID_ANNONCE + " FROM ( " + TABLE_ANNONCE + " ) WHERE " + COL_LOCATION_ANNONCE + " = " + location;


            if(!et_inf_prix.getText().toString().equals("")&&!et_sup_prix.getText().toString().equals("")){
                request = "SELECT " + COL_ID_ANNONCE + " FROM ( " + TABLE_ANNONCE + " ) WHERE " + COL_PRIX_ANNONCE + " BETWEEN " +
                        et_inf_prix.getText() + " AND " + et_sup_prix.getText() + " IN ("+ request +" )";
            }

            for(int i=0;i<et_List.size();i=i+2){//TODO qd il n'y a qu'une borne
                if(!et_List.get(i).getText().toString().equals("")&&!et_List.get(i+1).getText().toString().equals("")){
                    request = "SELECT " + COL_ANNONCE_CRITERE_ANNONCE + " FROM ( " + TABLE_CRITERE_ANNONCE + " ) WHERE " + COL_CRITERE_CRITERE_ANONCE +" ='"+ critere_et_list.get(i).getId()  +
                            "' AND " + COL_VALEUR_CRITERE_ANNONCE + " BETWEEN " + et_List.get(i).getText() + " AND " + et_List.get(i+1).getText() +
                            " AND " + COL_ANNONCE_CRITERE_ANNONCE + " IN ("+ request +" )";
                }
            }

            for(int i=0;i<spinner_List.size();i++){
               if(!spinner_List.get(i).getSelectedItem().toString().equals("")){
                   request = "SELECT " + COL_ANNONCE_CRITERE_ANNONCE + " FROM " + TABLE_CRITERE_ANNONCE + " WHERE " + COL_CRITERE_CRITERE_ANONCE +" ='"+ critere_spinner_list.get(i).getId()  +
                           "' AND " + COL_VALEUR_CRITERE_ANNONCE + " ='" + spinner_List.get(i).getSelectedItem().toString() +
                           "' AND " + COL_ANNONCE_CRITERE_ANNONCE + " IN ("+ request +" )";
               }
            }

            //Barre de recherche
            EditText et_barre = findViewById(R.id.edit_text_recherche_recherche);
            String text = et_barre.getText().toString();
            String[] mots = text.split(" ");
            for(String m : mots){
                request = "SELECT " + COL_ID_ANNONCE + " FROM " + TABLE_ANNONCE + " WHERE ( " + COL_TITRE_ANNONCE + " LIKE " + "'%" + m + "%'" +
                        " OR "+ COL_LIEU_ANNONCE + " LIKE " + "'%" + m + "%'" +
                        " OR "+ COL_DESCRITPION_ANNONCE + " LIKE " + "'%" + m + "%'" +
                        " ) AND " + COL_ANNONCE_CRITERE_ANNONCE + " IN ("+ request +" )";
            }

            MaBaseSQLite maBaseSQLite = new MaBaseSQLite(getApplicationContext(), NOM_BD, null, 1);
            SQLiteDatabase bd = maBaseSQLite.getWritableDatabase();

            Cursor c = bd.rawQuery(request,null);
            List<Integer> res = new ArrayList<>();
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                res.add(c.getInt(0));
                c.moveToNext();
            }
            c.close();
            bd.close();

            List<Annonce> annonces = new ArrayList<>();
            for(int i : res){
                annonces.add(ABD.getAnnonceById(i));
            }

            List<Annonce> avecPromotion = new ArrayList<>();
            List<Annonce> sansPromotion = new ArrayList<>();
            for(Annonce a : annonces){
                if(a.getPromotion()==YES){
                    avecPromotion.add(a);
                }
                else{
                    sansPromotion.add(a);
                }
            }
            annonces.clear();
            annonces.addAll(avecPromotion);
            annonces.addAll(sansPromotion);
            affichageAnnonces(annonces);
        });

        this.dynamic = findViewById(R.id.dynamic_annonce_recherche);

      /*  List<Annonce> annonces= ABD.getAllAnnonces();
        if(annonces!=null){
            List<Annonce> avecPromotion = new ArrayList<>();
            List<Annonce> sansPromotion = new ArrayList<>();
            for(Annonce a : annonces){
                if(a.getPromotion()==YES){
                    avecPromotion.add(a);
                }
                else{
                    sansPromotion.add(a);
                }
            }
            annonces.clear();
            annonces.addAll(avecPromotion);
            annonces.addAll(sansPromotion);
        }
        this.affichageAnnonces(annonces);*/
    }

    private void affichageAnnonces(List<Annonce> annonces) {
        this.dynamic.removeAllViews();
        for(int i = 0; i < annonces.size(); i++) {
            AnnonceView annonce = new AnnonceView(this,annonces.get(i),currentUserId);
           // AnnonceView annonce = new AnnonceView(this, "date : " + i, annonces.get(i).getPrix(), annonces.get(i).getTitre(), annonces.get(i).getLieu());

            this.dynamic.addView(annonce);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO garder la recherche en mémoire
        AnnonceBD ABD = new AnnonceBD(this);
        List<Annonce> annonces= ABD.getAllAnnonces();
        if(annonces!=null){
            List<Annonce> avecPromotion = new ArrayList<>();
            List<Annonce> sansPromotion = new ArrayList<>();
            for(Annonce a : annonces){
                if(a.getPromotion()==YES){
                    avecPromotion.add(a);
                }
                else{
                    sansPromotion.add(a);
                }
            }
            annonces.clear();
            annonces.addAll(avecPromotion);
            annonces.addAll(sansPromotion);
        }
        assert annonces != null;
        this.affichageAnnonces(annonces);

    }
}