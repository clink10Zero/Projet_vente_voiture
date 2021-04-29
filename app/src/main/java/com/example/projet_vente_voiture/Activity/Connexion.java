package com.example.projet_vente_voiture.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.UtilisateurBD;
import com.example.projet_vente_voiture.Object.Utilisateur;
import com.example.projet_vente_voiture.R;

import androidx.annotation.NonNull;

public class Connexion extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        setSupportActionBar(findViewById(R.id.toolbar));

        if(currentUserId!=-1){
            finish();
        }

        EditText mail =  findViewById(R.id.edit_text_email_connexion);
        EditText mdp = findViewById(R.id.edit_text_mot_de_passe_connexion);

        Button btn_validation = findViewById(R.id.button_validation_connexion);
        Button btn_inconnu = findViewById(R.id.button_inconnu_connexion);

        btn_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mail.getText().toString().equals("") || mdp.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Le mail ou le mot de passe n'est pas rempli.", Toast.LENGTH_LONG).show();
                }
                else{
                    UtilisateurBD UBD = new UtilisateurBD(getApplicationContext());
                    Utilisateur util = UBD.getUtilisateurByMail(mail.getText().toString());

                    if(util == null || !util.getMdp().equals(mdp.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Mail et/ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),Recherche.class);
                        intent.putExtra("currentUser",util.getId());
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        btn_inconnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Recherche.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_connection:
                Toast.makeText(getApplicationContext(), "Vous êtes déjà sur la page de connexion", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_inscription:
                intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_deconnection:
                intent = new Intent(getApplicationContext(), Connexion.class);
                startActivity(intent);
                finish();;
                break;
            case R.id.action_mes_annonces:
                intent = new Intent(getApplicationContext(), Mes_annonces.class);
                intent.putExtra("currentUser",currentUserId);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}