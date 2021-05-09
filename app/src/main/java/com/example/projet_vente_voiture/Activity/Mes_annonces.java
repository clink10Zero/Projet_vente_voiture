package com.example.projet_vente_voiture.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceView;
import com.example.projet_vente_voiture.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Mes_annonces extends General {

    List<Annonce> annonces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces);
        setSupportActionBar(findViewById(R.id.toolbar));

        AnnonceBD ABD = new AnnonceBD(this);
        this.annonces= ABD.getAnnoncesByUserId(currentUserId);
        if(annonces!=null){
            affichageAnnonces(annonces);
        }

        FloatingActionButton btn_nouvelle_annonce = findViewById(R.id.button_nouvelle_annonce_mes_annonces);
        btn_nouvelle_annonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Ajouter_Annonce.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.currentUserId!=((MyApp) getApplication()).getCurrentUserId()){
            Intent intent = new Intent(getApplicationContext(), this.getClass());
            getApplicationContext().startActivity(intent);
            finish();
        }

        AnnonceBD ABD = new AnnonceBD(this);
        List<Annonce> annoncesToCompare= ABD.getAnnoncesByUserId(currentUserId);
        if(this.annonces!=null) {
            if (this.annonces != annoncesToCompare) {
                this.annonces=annoncesToCompare;
                affichageAnnonces(annonces);
            }
        }
    }

    private void affichageAnnonces(List<Annonce> annonces) {
        LinearLayout ll_dynamic = findViewById(R.id.dynamique_linear_layout_mes_annonces);
        ll_dynamic.removeAllViews();
        for(int i = 0; i < annonces.size(); i++) {
            AnnonceView annonce = new AnnonceView(this,annonces.get(i));
            ll_dynamic.addView(annonce);
        }
    }
}