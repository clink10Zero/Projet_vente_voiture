package com.example.projet_vente_voiture.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projet_vente_voiture.R;

public class General extends AppCompatActivity {

    protected int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserId = getIntent().getIntExtra("currentUser",-1);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentUserId != -1) {
            getMenuInflater().inflate(R.menu.menu_toolbar_co, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_toolbar_non_co, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_connection:
                intent = new Intent(getApplicationContext(), Connexion.class);
                startActivity(intent);
                break;
            case R.id.action_inscription:
                intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
                break;
            case R.id.action_deconnection:
                break;
            case R.id.action_mes_annonces:
                intent = new Intent(getApplicationContext(), Mes_annonces.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
