package com.example.projet_vente_voiture.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.projet_vente_voiture.Object.AnnonceView;
import com.example.projet_vente_voiture.R;

import org.w3c.dom.Text;

import java.util.Date;

public class Recherche extends AppCompatActivity {

    private LinearLayout dynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        this.dynamic = (LinearLayout) findViewById(R.id.dynamic_annonce_recherche);
        this.affichageAnnonces();
    }

    private void affichageAnnonces() {
        for(int i = 0; i < 10; i++) {
            AnnonceView annonce = new AnnonceView(this, "date : " + i, i, "titre : " + i, "lieu : " + i);

            this.dynamic.addView(annonce);
        }
    }
}