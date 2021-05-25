package com.example.projet_vente_voiture.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.UtilisateurBD;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.ResultatForm;
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NO;

public class ModifProfil extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_profil);
        setSupportActionBar(findViewById(R.id.toolbar));

        UtilisateurBD UBD = new UtilisateurBD(getApplicationContext());
        Utilisateur user = UBD.getUtilisateurById(this.currentUserId);

        EditText nom =  findViewById(R.id.edit_text_nom_modif);
        nom.setText(user.getNom());
        EditText prenom = findViewById(R.id.edit_text_prenom_modif);
        prenom.setText(user.getPrenom());
        EditText mail = findViewById(R.id.edit_text_email_modif);
        mail.setText(user.getMail());
        EditText mdp = findViewById(R.id.edit_text_mot_de_passe_modif);
        EditText confirmationMDP =  findViewById(R.id.edit_text_confirmation_mot_de_passe_modif);

        Button btn_validation = findViewById(R.id.button_validation_modif);

        btn_validation.setOnClickListener(v -> {
            ResultatForm test = isFormOK(mail, mdp, confirmationMDP);
            if (!test.getBool()) {
                Toast.makeText(getApplicationContext(), test.getText(), Toast.LENGTH_LONG).show();
            } else {
                Utilisateur testUser = UBD.getUtilisateurByMail(mail.getText().toString());
                if (testUser != null && testUser != user) {
                    Toast.makeText(getApplicationContext(), "Cette adresse mail est déjà associée à un compte", Toast.LENGTH_LONG).show();
                } else {
                    user.setNom(nom.getText().toString());
                    user.setPrenom(prenom.getText().toString());
                    user.setMail(mail.getText().toString());
                    if(!mdp.getText().toString().equals("")) {
                        user.setMdp(mdp.getText().toString());
                    }
                    UBD.updateUtilisateur(this.currentUserId, user);
                    Toast.makeText(getApplicationContext(), "mise à jour des données faite", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Recherche.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private ResultatForm isFormOK(EditText mail , EditText mdp , EditText confirmationMDP) {
        String text = "";
        boolean bool = false;
        if (mail.getText().toString().equals("")) {
            text = "L'adresse mail n'est pas rentrée.";
        } else {
            if (!mdp.getText().toString().equals(confirmationMDP.getText().toString())) {
                text = "Les mots de passe ne sont pas identiques.";
            }
        }
        if(text.equals("")){
            bool=true;
        }
        return new ResultatForm(bool,text);
    }
}