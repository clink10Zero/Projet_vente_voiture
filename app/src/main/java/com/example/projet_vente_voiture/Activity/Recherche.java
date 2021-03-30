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

import com.example.projet_vente_voiture.R;

import org.w3c.dom.Text;

public class Recherche extends AppCompatActivity {

    private LinearLayout dynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        this.dynamic = (LinearLayout) findViewById(R.id.dynamic_annonce_recherche);

        for(int i = 0; i < 10; i++) {
            LinearLayout de = new LinearLayout(this);
            de.setOrientation(LinearLayout.HORIZONTAL);
            TextView date = new TextView(this);
            date.setText("DATE " + i);
            TextView prix = new TextView(this);
            date.setText("PRIX " + i);

            de.addView(date);
            de.addView(prix);

            LinearLayout tlde = new LinearLayout(this);
            tlde.setOrientation(LinearLayout.VERTICAL);
            TextView titre = new TextView(this);
            titre.setText("TITRE " + i);
            Space s = new Space(this);
            LinearLayout.LayoutParams ls = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
            TextView lieu = new TextView(this);
            lieu.setText("LIEU " + i);

            tlde.addView(titre);
            tlde.addView(s, ls);
            tlde.addView(lieu);
            tlde.addView(de);

            LinearLayout annonce = new LinearLayout(this);
            Button imgb = new Button(this);
            imgb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.poule, 0, 0, 0);

            annonce.addView(imgb);
            annonce.addView(tlde);

            this.dynamic.addView(annonce);
        }

    }
}