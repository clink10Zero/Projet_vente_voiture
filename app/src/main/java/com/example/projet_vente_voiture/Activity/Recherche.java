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

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceView;
import com.example.projet_vente_voiture.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        AnnonceBD ABD = new AnnonceBD(getApplicationContext());
        List<Annonce> annonces = ABD.getAllAnnonces();
        for(int i = 0; i < annonces.size(); i++) {
            AnnonceView annonce = new AnnonceView(this, "date : " + i, annonces.get(i).getPrix(), annonces.get(i).getTitre(), annonces.get(i).getLieu());

            this.dynamic.addView(annonce);
        }
    }
}