package com.example.projet_vente_voiture.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.AnnonceSauvegardeBD;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceSauvegarde;
import com.example.projet_vente_voiture.View.AnnonceView;
import com.example.projet_vente_voiture.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.YES;

public class Mes_annonces_sauvergardes extends General {

    List<Annonce> annonces;
    int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces_sauvergardes);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.poule0);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (currentUserId == -1) {
            finish();
        }

        AnnonceSauvegardeBD ASBD = new AnnonceSauvegardeBD(this);
        ArrayList<AnnonceSauvegarde> as = ASBD.getAnnonceSauvegardeByUserId(currentUserId);
        annonces = annoncesSauvergardesToAnnonces(as);
        affichageAnnonces(annonces);

        SwitchCompat switch_vente=findViewById(R.id.switch_vente_mes_annonces_sauvegardes);
        SwitchCompat switch_loc=findViewById(R.id.switch_location_mes_annonces_sauvegardes);

        if(switch_vente.isChecked()){
            location=NO;
            switch_loc.setChecked(false);
        }

        switch_vente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked) {
                    location=NO;
                    if(switch_loc.isChecked()) {
                        switch_loc.setChecked(false);
                    }
                }
                else{
                    location=YES;
                    if(!switch_loc.isChecked()){
                        switch_loc.setChecked(true);
                    }
                }
                annonces=annoncesSauvergardesToAnnoncesWithLocationOption(ASBD.getAnnonceSauvegardeByUserId(currentUserId));
                affichageAnnonces(annonces);
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
                annonces=annoncesSauvergardesToAnnoncesWithLocationOption(ASBD.getAnnonceSauvegardeByUserId(currentUserId));
                affichageAnnonces(annonces);
            }
        });
    }

    private void affichageAnnonces(List<Annonce> annonces) {
        LinearLayout ll_dynamic = findViewById(R.id.dynamique_linear_layout_mes_annonces_sauvegardes);
        ll_dynamic.removeAllViews();
        if(annonces!=null) {
            for (int i = 0; i < annonces.size(); i++) {
                AnnonceView annonce = new AnnonceView(this, annonces.get(i), currentUserId);
                ll_dynamic.addView(annonce);
            }
        }
    }

    @Override
    protected void onResume() {//TODO revoir les OnResume de manière générale
        super.onResume();
        if (this.currentUserId != ((MyApp) getApplication()).getCurrentUserId()) {
            Intent intent = new Intent(getApplicationContext(), this.getClass());
            getApplicationContext().startActivity(intent);
            finish();
        }

        AnnonceSauvegardeBD ASBD = new AnnonceSauvegardeBD(this);
        ArrayList<Integer> list = ASBD.getIDAnnonceSauvegardeByUserId(currentUserId);
        annonces = new ArrayList<>();
        if (list != null) {
            AnnonceBD ABD = new AnnonceBD(this);
            for (int i : list) {
                annonces.add(ABD.getAnnonceById(i));
            }
        }
        affichageAnnonces(annonces);
    }

    private List<Annonce> annoncesSauvergardesToAnnonces(List<AnnonceSauvegarde> annoncesSauvegardes) {
        if (annoncesSauvegardes != null) {
            AnnonceBD ABD = new AnnonceBD(this);
            List<Annonce> res = new ArrayList<>();
            for (AnnonceSauvegarde as : annoncesSauvegardes) {
                res.add(ABD.getAnnonceById(as.getAnnonce_id()));
            }
            return res;
        }
        return null;
    }

    private List<Annonce> annoncesSauvergardesToAnnoncesWithLocationOption(List<AnnonceSauvegarde> annoncesSauvegardes) {
        if (annoncesSauvegardes != null) {
            AnnonceBD ABD = new AnnonceBD(this);
            List<Annonce> res = new ArrayList<>();
            for (AnnonceSauvegarde as : annoncesSauvegardes) {
                Annonce a = ABD.getAnnonceById(as.getAnnonce_id());
                if(a.getLocation()==location){
                    res.add(a);
                }
            }
            return res;
        }
        return null;
    }
}