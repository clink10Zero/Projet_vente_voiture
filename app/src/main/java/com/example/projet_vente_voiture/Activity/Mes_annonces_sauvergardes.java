package com.example.projet_vente_voiture.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.AnnonceSauvegardeBD;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceView;
import com.example.projet_vente_voiture.R;

import java.util.ArrayList;
import java.util.List;

public class Mes_annonces_sauvergardes extends General {

    List<Annonce> annonces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces_sauvergardes);
        setSupportActionBar(findViewById(R.id.toolbar));

        if(currentUserId==-1){
            finish();
        }

        AnnonceSauvegardeBD ASBD = new AnnonceSauvegardeBD(this);
        ArrayList<Integer> list = ASBD.getIDAnnonceSauvegardeByUserId(currentUserId);

        if(list!=null){
            AnnonceBD ABD = new AnnonceBD(this);
            annonces=new ArrayList<>();
            for(int i : list){
                annonces.add(ABD.getAnnonceById(i));
            }
            affichageAnnonces(annonces);
        }

    }

    private void affichageAnnonces(List<Annonce> annonces) {
        LinearLayout ll_dynamic = findViewById(R.id.dynamique_linear_layout_mes_annonces_sauvegardes);
        ll_dynamic.removeAllViews();
        for(int i = 0; i < annonces.size(); i++) {
            AnnonceView annonce = new AnnonceView(this,annonces.get(i));
            ll_dynamic.addView(annonce);
        }
    }

   @Override
    protected void onResume() {
        super.onResume();
        if(this.currentUserId!=((MyApp) getApplication()).getCurrentUserId()){
            Intent intent = new Intent(getApplicationContext(), this.getClass());
            getApplicationContext().startActivity(intent);
            finish();
        }

        AnnonceSauvegardeBD ASBD = new AnnonceSauvegardeBD(this);
        ArrayList<Integer> list = ASBD.getIDAnnonceSauvegardeByUserId(currentUserId);
        annonces= new ArrayList<>();
        if(list!=null) {
            AnnonceBD ABD = new AnnonceBD(this);
            for(int i : list){
                annonces.add(ABD.getAnnonceById(i));
            }
        }
        affichageAnnonces(annonces);
    }
}