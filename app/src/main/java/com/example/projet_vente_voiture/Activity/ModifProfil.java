package com.example.projet_vente_voiture.Activity;

import android.os.Bundle;

import com.example.projet_vente_voiture.R;

public class ModifProfil extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_profil);
        setSupportActionBar(findViewById(R.id.toolbar));
    }
}