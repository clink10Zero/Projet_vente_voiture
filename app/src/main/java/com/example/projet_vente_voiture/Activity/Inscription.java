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
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

public class Inscription extends AppCompatActivity implements View.OnClickListener {

    private EditText nom;
    private EditText prenom;
    private EditText mail;
    private EditText confirmationMail;
    private EditText mdp;
    private EditText confirmationMDP;
    private RadioGroup proPer;

    private Button validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        this.nom = (EditText) findViewById(R.id.edit_text_nom_inscription);
        this.prenom = (EditText) findViewById(R.id.edit_text_prenom_inscription);
        this.mail = (EditText) findViewById(R.id.edit_text_email_inscription);
        this.confirmationMail = (EditText) findViewById(R.id.edit_text_confirmation_email_inscription);
        this.mdp = (EditText) findViewById(R.id.edit_text_mot_de_passe_inscription);
        this.confirmationMDP = (EditText) findViewById(R.id.edit_text_confirmation_mot_de_passe_inscription);
        this.proPer = (RadioGroup) findViewById(R.id.radio_group_categorie_inscription);
        this.validation = (Button) findViewById(R.id.button_validation_inscription);

        this.validation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_validation_inscription :
                if(!this.mail.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "l'adresse mail n'est pas rentré.", Toast.LENGTH_LONG).show();
                    break;
                }
                if(!this.mail.getText().toString().equals(this.confirmationMail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "les adresse mail ne sont pas identique.", Toast.LENGTH_LONG).show();
                    break;
                }
                if(!this.mdp.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "le mots de passe n'est pas rentré.", Toast.LENGTH_LONG).show();
                    break;
                }
                if(!this.mdp.getText().toString().equals(this.confirmationMDP.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "les mots de passe ne sont pas identique.", Toast.LENGTH_LONG).show();
                    break;
                }
                int idCheked = this.proPer.getCheckedRadioButtonId();
                int value = -1;

                switch (idCheked) {
                    case R.id.radio_buttton_professionnel_inscirption :
                        value = 0;
                        break;
                    case R.id.radio_buttton_particulier_inscirption :
                        value = 1;
                        break;
                }

                if(value == -1) {
                    Toast.makeText(getApplicationContext(), "vous n'avez pas spécifier si vous étes un professionnel ou un particulier", Toast.LENGTH_LONG).show();
                    break;
                }
                Utilisateur util = new Utilisateur(this.prenom.getText().toString(), this.nom.getText().toString(), this.mail.getText().toString(), this.mdp.getText().toString(), value);
                UtilisateurBD uBD = new UtilisateurBD(this);
                uBD.insertUtilisateur(util);
                getApplicationContext().startActivity(new Intent(getApplicationContext(), Recherche.class));
                break;
        }
    }
}