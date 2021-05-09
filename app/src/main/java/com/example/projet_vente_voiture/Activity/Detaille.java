package com.example.projet_vente_voiture.Activity;

import android.app.Activity;
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
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.ConfirmPopUp;
import com.example.projet_vente_voiture.Object.CritereAnnonce;
import com.example.projet_vente_voiture.R;

import java.util.List;

public class Detaille extends General {

    int id_annonce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaille);
        setSupportActionBar(findViewById(R.id.toolbar));

        Intent intent = getIntent();
        this.id_annonce = intent.getIntExtra("id",-1);

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

            LinearLayout ll_btn =findViewById(R.id.linear_layout_button_detail);
            int id_auteur = annonce.getId_auteur();
            if(id_auteur==currentUserId){
                Button btn_modif = new Button(this);
                btn_modif.setText("Modifier");
                btn_modif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getApplicationContext(),Ajouter_Annonce.class);
                        intent1.putExtra("currentAnnonce",id_annonce);
                        startActivity(intent1);
                    }
                });

                ll_btn.addView(btn_modif);

                Activity activity =this;
                Button btn_suppr = new Button(this);
                btn_suppr.setText("Supprimer");
                btn_suppr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ConfirmPopUp confirmPopUp = new ConfirmPopUp(activity);
                        confirmPopUp.setTitle("Voulez-vous vraiment supprimer l'annonce '"+ annonce.getTitre() + "' ?");
                        confirmPopUp.getConfirmButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ABD.removeAnnonceWithID(id_annonce);
                                confirmPopUp.dismiss();
                                activity.finish();
                                Intent intent1 = new Intent(getApplicationContext(),Mes_annonces.class);
                                startActivity(intent1);
                            }
                        });
                        confirmPopUp.getCancelButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                confirmPopUp.dismiss();
                            }
                        });
                        confirmPopUp.build();
                    }});

                ll_btn.addView(btn_suppr);

            }else {
                Button btn_contact = new Button(this);
                btn_contact.setText("Contact");
                btn_contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Page de contact à faire", Toast.LENGTH_LONG).show();
                    }
                });
                ll_btn.addView(btn_contact);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.currentUserId!=((MyApp) getApplication()).getCurrentUserId()){
            Intent intent = new Intent(getApplicationContext(), this.getClass());
            intent.putExtra("id",this.id_annonce);
            getApplicationContext().startActivity(intent);
            finish();
        }
    }
}