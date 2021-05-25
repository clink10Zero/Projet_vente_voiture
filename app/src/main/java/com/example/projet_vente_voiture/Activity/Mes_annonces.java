package com.example.projet_vente_voiture.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.View.AnnonceView;
import com.example.projet_vente_voiture.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.widget.SwitchCompat;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.YES;

public class Mes_annonces extends General {

    List<Annonce> annonces;
    int location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces);
        setSupportActionBar(findViewById(R.id.toolbar));

        AnnonceBD ABD = new AnnonceBD(this);
        /*this.annonces= ABD.getAnnoncesByUserId(currentUserId);
        affichageAnnonces(annonces);*/

        FloatingActionButton btn_nouvelle_annonce = findViewById(R.id.button_nouvelle_annonce_mes_annonces);
        btn_nouvelle_annonce.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Ajouter_Annonce.class);
            startActivity(intent);
        });

        SwitchCompat switch_vente=findViewById(R.id.switch_vente_mes_annonces);
        SwitchCompat switch_loc=findViewById(R.id.switch_location_mes_annonces);

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
                annonces=ABD.getAnnoncesByUserIdAndIfIsLocation(currentUserId,location);
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
                annonces=ABD.getAnnoncesByUserIdAndIfIsLocation(currentUserId,location);
                affichageAnnonces(annonces);
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
        if(annonces!=null) {
            for (int i = 0; i < annonces.size(); i++) {
                AnnonceView annonce = new AnnonceView(this, annonces.get(i), currentUserId);
                ll_dynamic.addView(annonce);
            }
        }
    }
}