package com.example.projet_vente_voiture.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.UtilisateurBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.PopUp.ConfirmPopUp;
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.YES;

public class GestionAbonnement extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_abonnement);
        setSupportActionBar(findViewById(R.id.toolbar));

        UtilisateurBD UBD = new UtilisateurBD(this);
        Utilisateur user = UBD.getUtilisateurById(currentUserId);

        TextView tv = findViewById(R.id.text_view_gestion_abonnement);
        Button btn_action = findViewById(R.id.btn_action_gestion_abonnement);

        Activity activity = this;
        String txt;

        if(user.getAbonnement()==NO){
            tv.setText("L'abonnement est blablabla ...");
            btn_action.setText("S'abonner");
            txt = "Etes-vous sur de vouloir vous abonnez ?\n (gratuit et annulable à tout moment)";

        }
        else{
            tv.setText("Vous disposez actuellement d'un abonnement");
            btn_action.setText("Résillier abonnement");
            txt="Etes-vous sur de vouloir vous désabonnez ?";
        }

        String finalTxt = txt;
        btn_action.setOnClickListener(v -> {
            final ConfirmPopUp confirmPopUp = new ConfirmPopUp(activity);
            confirmPopUp.setTitle(finalTxt);
            confirmPopUp.getConfirmButton().setOnClickListener(view -> {
                if(user.getAbonnement()==YES){
                    user.setAbonnement(NO);
                    AnnonceBD ABD = new AnnonceBD(getApplicationContext());
                    List<Annonce> promToRemove = ABD.getPromotedAnnoncesByUserId(currentUserId);
                    if(promToRemove!=null){
                        for(Annonce a : promToRemove){
                            a.setPromotion(NO);
                            ABD.updateAnnonce(a.getId(),a);
                        }
                    }
                }
                else{
                    user.setAbonnement(YES);
                }
                UBD.updateUtilisateur(currentUserId,user);
                confirmPopUp.dismiss();
                activity.finish();
                startActivity(new Intent(getApplicationContext(),GestionAbonnement.class));
            });
            confirmPopUp.getCancelButton().setOnClickListener(view -> confirmPopUp.dismiss());
            confirmPopUp.build();
        });
    }
}