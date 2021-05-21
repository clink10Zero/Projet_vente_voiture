package com.example.projet_vente_voiture.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.AnnonceSauvegardeBD;
import com.example.projet_vente_voiture.BD.CritereAnnonceBD;
import com.example.projet_vente_voiture.BD.CritereBD;
import com.example.projet_vente_voiture.BD.UtilisateurBD;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceSauvegarde;
import com.example.projet_vente_voiture.Object.ConfirmPopUp;
import com.example.projet_vente_voiture.Object.CritereAnnonce;
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.YES;

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

            ImageButton btn_fav = findViewById(R.id.btn_fav_detaille);
            if(currentUserId!=-1){
                AnnonceSauvegardeBD ASBD = new AnnonceSauvegardeBD(this);
                ArrayList<Integer> fav_list = ASBD.getIDAnnonceSauvegardeByUserId(currentUserId);
                if(fav_list!=null){
                    if(fav_list.contains(id_annonce)){
                        btn_fav.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
                    }
                }
            }

            btn_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currentUserId==-1){
                        Toast.makeText(getApplicationContext(), "Vous devez être connecté·e pour ajouter une annonce à vos favoris", Toast.LENGTH_LONG).show();
                    }
                    else{
                        AnnonceSauvegardeBD ASBD = new AnnonceSauvegardeBD(getApplicationContext());
                        ArrayList<Integer> fav_list = ASBD.getIDAnnonceSauvegardeByUserId(currentUserId);
                        if(fav_list!=null && fav_list.contains(id_annonce)){
                            btn_fav.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
                            ASBD.removeAnnonceSauvegardeWithUserAndAnnonceID(currentUserId,id_annonce);
                        }
                        else{
                            btn_fav.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
                            ASBD.insertAnnonceSauvegarde(new AnnonceSauvegarde(currentUserId,id_annonce));
                        }

                    }
                }
            });


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
                Activity activity =this;

                Button btn_modif = new Button(this);
                btn_modif.setText("Modifier");
                btn_modif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),Ajouter_Annonce.class);
                        intent.putExtra("currentAnnonce",id_annonce);
                        startActivity(intent);
                    }
                });

                ll_btn.addView(btn_modif);

                UtilisateurBD UBD = new UtilisateurBD(this);
                Utilisateur user = UBD.getUtilisateurById(currentUserId);
                if(user.getAbonnement()==YES){
                    Button btn_promotion = new Button(this);
                    String text_promotion = "Promouvoir annonce";
                    String txtPopUp = "Etes vous sur de vouloir promouvoir cette annonce ?";
                    if(annonce.getPromotion()==YES){
                        text_promotion = "Retirer mise en avant de l'annonce";
                        txtPopUp = "Etes vous sur de vouloir annuler la mise en avant de cette annonce ?";
                    }

                    btn_promotion.setText(text_promotion);
                    String finalTxtPopUp = txtPopUp;
                    btn_promotion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ConfirmPopUp confirmPopUp = new ConfirmPopUp(activity);
                            confirmPopUp.setTitle(finalTxtPopUp);
                            confirmPopUp.getConfirmButton().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(annonce.getPromotion()==YES){
                                        annonce.setPromotion(NO);
                                    }
                                    else{
                                        annonce.setPromotion(YES);
                                    }

                                    ABD.updateAnnonce(id_annonce,annonce);

                                    confirmPopUp.dismiss();
                                    activity.finish();

                                    Intent intent = new Intent(getApplicationContext(),Detaille.class);
                                    intent.putExtra("id",id_annonce);
                                    startActivity(intent);
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

                    ll_btn.addView(btn_promotion);
                }

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

            }
            else {
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