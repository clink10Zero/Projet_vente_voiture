package com.example.projet_vente_voiture.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.CritereAnnonceBD;
import com.example.projet_vente_voiture.BD.CritereBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.CritereAnnonce;
import com.example.projet_vente_voiture.R;

import java.util.List;

public class Detaille extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaille);

        Intent intent = getIntent();
        int id_annonce = intent.getIntExtra("id",-1);
        int currentUserId = intent.getIntExtra("currentUser",-1);


        AnnonceBD ABD = new AnnonceBD(this);
        Annonce annonce = ABD.getAnnonceById(id_annonce);

        if(annonce!=null) {

            TextView tv_titre = findViewById(R.id.text_view_titre_detail);
            tv_titre.setText(annonce.getTitre());

            TextView tv_prix = findViewById(R.id.text_view_prix_detail);
            tv_prix.setText(annonce.getPrix()+"€");

            TextView tv_date = findViewById(R.id.text_view_date_detail);
            tv_date.setText(annonce.getDate());

            TextView tv_lieu = findViewById(R.id.text_view_lieu_detail);
            tv_lieu.setText(annonce.getLieu());

            CritereAnnonceBD CABD = new CritereAnnonceBD(this);
            List<CritereAnnonce>  criteres = CABD.getCritereAnnonceByAnnonceId(id_annonce);
            if(criteres!=null){
                LinearLayout ll_critere = findViewById(R.id.dynamic_linear_layout_criteres_detail);
                CritereBD CBD = new CritereBD(this);
                for(int i=0;i<criteres.size();i++){
                    int id_critere = criteres.get(i).getId_critere();
                    String nom_critere = CBD.getCritereById(id_critere).getNom();
                    TextView tv_critere = new TextView(this);
                    tv_critere.setText(nom_critere+ " : "+ criteres.get(i).getValeur());
                    ll_critere.addView(tv_critere);
                }
            }

            TextView tv_description = findViewById(R.id.text_view_description_detail);
            tv_description.setText(annonce.getDescritpion());

            int id_auteur = annonce.getId_auteur();
            Button btn_contact = findViewById(R.id.button_contact_detail);
            btn_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /* Intent intent = new Intent(this, Detaille.class);
                intent.putExtra("id_auteur",id_auteur);
                startActivity(intent);*/
                    Toast.makeText(getApplicationContext(), "Page de contact à faire", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}