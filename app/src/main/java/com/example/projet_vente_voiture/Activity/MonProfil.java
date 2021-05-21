package com.example.projet_vente_voiture.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projet_vente_voiture.Activity.General;
import com.example.projet_vente_voiture.Activity.Mes_annonces;
import com.example.projet_vente_voiture.Activity.Mes_annonces_sauvergardes;
import com.example.projet_vente_voiture.BD.UtilisateurBD;
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

public class MonProfil extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil);
        setSupportActionBar(findViewById(R.id.toolbar));

        Button btn_modif = findViewById(R.id.btn_modif_mon_profil);
        Button btn_mes_annonces = findViewById(R.id.btn_mes_annonces_mon_profil);
        Button btn_annonces_save = findViewById(R.id.btn_annonces_sauvegardes_mon_profil);
        Button btn_stats = findViewById(R.id.btn_stats_mon_profil);

        btn_modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ModifProfil.class));
            }
        });

        btn_mes_annonces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Mes_annonces.class));
            }
        });

        btn_annonces_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Mes_annonces_sauvergardes.class));
            }
        });

        btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Statistiques.class));
            }
        });

        UtilisateurBD UBD = new UtilisateurBD(this);
        Utilisateur user = UBD.getUtilisateurById(currentUserId);
        if(user.getProfessionel()==Utilisateur.PROFESSIONEL){
            LinearLayout ll = findViewById(R.id.ll_mon_profil);
            Button btn_gestion_abonnement = new Button(this);
            btn_gestion_abonnement.setText("Gestion abonnement");
            btn_gestion_abonnement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity((new Intent(getApplicationContext(), GestionAbonnement.class)));
                }
            });
            ll.addView(btn_gestion_abonnement);
        }
    }
}