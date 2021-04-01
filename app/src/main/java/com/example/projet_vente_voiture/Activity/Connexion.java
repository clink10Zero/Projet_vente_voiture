package com.example.projet_vente_voiture.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        this.mail = (EditText) findViewById(R.id.edit_text_email_connexion);
        this.mdp = (EditText) findViewById(R.id.edit_text_mot_de_passe_connexion);
        this.validation = (Button) findViewById(R.id.button_validation_connexion);

        this.validation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_validation_connexion :
                if(this.mail.getText().toString().equals("") || this.mdp.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "soit le mail ou le mots de passe n'est pas remplis.", Toast.LENGTH_LONG).show();
                    break;
                }
                UtilisateurBD uBD = new UtilisateurBD(this);
                Utilisateur util = uBD.getUtilisateurByMail(this.mail.getText().toString());
                if(util == null) {
                    Toast.makeText(getApplicationContext(), "soit le mail ou le mots de passe n'est pas bon", Toast.LENGTH_LONG).show();
                    break;
                }

                break;
        }
    }
}