package com.example.projet_vente_voiture.Activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceView;
import com.example.projet_vente_voiture.R;

import java.util.List;

public class Recherche extends General {

    private LinearLayout dynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        setSupportActionBar(findViewById(R.id.toolbar));

        this.dynamic = findViewById(R.id.dynamic_annonce_recherche);
        this.affichageAnnonces(currentUserId);
    }

    private void affichageAnnonces(int currentUserId) {
        AnnonceBD ABD = new AnnonceBD(getApplicationContext());
        List<Annonce> annonces = ABD.getAllAnnonces();
        for(int i = 0; i < annonces.size(); i++) {
            AnnonceView annonce = new AnnonceView(this,annonces.get(i),currentUserId);
           // AnnonceView annonce = new AnnonceView(this, "date : " + i, annonces.get(i).getPrix(), annonces.get(i).getTitre(), annonces.get(i).getLieu());

            this.dynamic.addView(annonce);
        }
    }
}