package com.example.projet_vente_voiture.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.UtilisateurBD;
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

public class Connexion extends AppCompatActivity implements View.OnClickListener {

    private EditText mail;
    private EditText mdp;
    private Button validation;
    private Button inscription;
    private Button inconnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        this.mail = (EditText) findViewById(R.id.edit_text_email_connexion);
        this.mdp = (EditText) findViewById(R.id.edit_text_mot_de_passe_connexion);
        this.validation = (Button) findViewById(R.id.button_validation_connexion);
        this.inscription = (Button) findViewById(R.id.button_inscription_connexion);
        this.inconnu = (Button) findViewById(R.id.button_inconnu_connexion);

        this.validation.setOnClickListener(this);
        this.inscription.setOnClickListener(this);
        this.inconnu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_validation_connexion :
                if(this.mail.getText().toString().equals("") || this.mdp.getText().toString().equals("")) {
                    Toast.makeText(this, "Le mail ou le mot de passe n'est pas rempli.", Toast.LENGTH_LONG).show();
                    break;
                }
                UtilisateurBD UBD = new UtilisateurBD(this);
                Utilisateur util = UBD.getUtilisateurByMail(this.mail.getText().toString());

                if(util == null || !util.getMdp().equals(this.mdp.getText().toString())) {
                    Toast.makeText(this, "Mail et/ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                    break;
                }

                intent = new Intent(this,Recherche.class);
            break;

            case R.id.button_inscription_connexion :
                intent = new Intent(this,Inscription.class);
            break;

            default :
                intent = new Intent(this,Recherche.class);
        }

        startActivity(intent);
    }
}