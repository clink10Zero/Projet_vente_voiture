package com.example.projet_vente_voiture.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.UtilisateurBD;
import com.example.projet_vente_voiture.Object.ResultatForm;
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

public class Inscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Intent intent = getIntent();
        boolean co = intent.getBooleanExtra("co",false);

        if(co){
            finish();
        }

        EditText nom =  findViewById(R.id.edit_text_nom_inscription);
        EditText prenom = findViewById(R.id.edit_text_prenom_inscription);
        EditText mail = findViewById(R.id.edit_text_email_inscription);
        EditText confirmationMail = findViewById(R.id.edit_text_confirmation_email_inscription);
        EditText mdp = findViewById(R.id.edit_text_mot_de_passe_inscription);
        EditText confirmationMDP =  findViewById(R.id.edit_text_confirmation_mot_de_passe_inscription);
        RadioGroup  proPer = findViewById(R.id.radio_group_categorie_inscription);

        Button btn_validation = findViewById(R.id.button_validation_inscription);
        Button btn_connexion = findViewById(R.id.button_connexion_inscription);
        Button btn_inconnu = findViewById(R.id.button_inconnu_inscription);

        btn_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultatForm test = isFormOK(mail, confirmationMail, mdp, mdp, proPer);
                if (!test.getBool()) {
                    Toast.makeText(getApplicationContext(), test.getText(), Toast.LENGTH_LONG).show();
                } else {
                    UtilisateurBD UBD = new UtilisateurBD(getApplicationContext());
                    Utilisateur user = UBD.getUtilisateurByMail(mail.getText().toString());
                    if (user != null) {
                        Toast.makeText(getApplicationContext(), "Cette adresse mail est déjà associée à un compte", Toast.LENGTH_LONG).show();
                    } else {
                        Utilisateur util = new Utilisateur(prenom.getText().toString(), nom.getText().toString(), mail.getText().toString(), mdp.getText().toString(), proPer.getCheckedRadioButtonId());
                        UBD.insertUtilisateur(util);

                        Intent intent = new Intent(getApplicationContext(), Recherche.class);
                        intent.putExtra("co", true);
                        startActivity(intent);

                        finish();
                    }

                }
            }
        });
        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Connexion.class);
                intent.putExtra("co",false);
                startActivity(intent);
                finish();
            }
        });

        btn_inconnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Recherche.class);
                intent.putExtra("co",false);
                startActivity(intent);
                finish();
            }
        });
    }

    private ResultatForm isFormOK(EditText mail , EditText confirmationMail , EditText mdp , EditText confirmationMDP , RadioGroup  proPer) {
        String text = "";
        boolean bool= false;
        if (mail.getText().toString().equals("")) {
            text="L'adresse mail n'est pas rentrée.";
        } else {
            if (!mail.getText().toString().equals(confirmationMail.getText().toString())) {
                text="Les adresses mail ne sont pas identiques.";
            } else {
                if (mdp.getText().toString().equals("")) {
                    text="Le mot de passe n'est pas rentré.";
                } else {
                    if (!mdp.getText().toString().equals(confirmationMDP.getText().toString())) {
                        text="Les mots de passe ne sont pas identiques.";
                    } else {
                        int idCheked = proPer.getCheckedRadioButtonId();
                        int value = -1;

                        switch (idCheked) {
                            case R.id.radio_buttton_professionnel_inscirption:
                                value = 0;
                                break;
                            case R.id.radio_buttton_particulier_inscirption:
                                value = 1;
                                break;
                        }

                        if (value == -1) {
                            text="Vous n'avez pas spécifié si vous êtes un professionnel ou un particulier";
                        }
                    }
                }
            }
        }
        if(text.equals("")){
            bool=true;
        }
        return new ResultatForm(bool,text);
    }
}