package com.example.projet_vente_voiture.Object;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.projet_vente_voiture.Activity.Detaille;
import com.example.projet_vente_voiture.R;

import java.util.Date;

public class AnnonceView extends LinearLayout {

    private TextView dateView;
    private TextView prixView;
    private TextView titreView;
    private TextView lieuView;
    private Button imgb;

    public AnnonceView(Context context, String date, int prix, String titre, String lieu) {
        super(context);

        //set DATE et PRIX
        LinearLayout de = new LinearLayout(context);
        de.setOrientation(LinearLayout.HORIZONTAL);

        this.dateView = new TextView(context);
        this.dateView.setText(date);
        //dateView.setHeight(de.getHeight()/2);

        this.prixView = new TextView(context);
        this.prixView.setText("prix : " + prix + " €");
        //prixView.setHeight(de.getHeight()/2);

        //ajout a la vue
        de.addView(dateView);
        de.addView(prixView);

        //set TIRE et LIEU
        LinearLayout tlde = new LinearLayout(context);
        tlde.setOrientation(LinearLayout.VERTICAL);
        this.titreView = new TextView(context);
        this.titreView.setText(titre);
        Space s = new Space(context);
        LinearLayout.LayoutParams ls = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        this.lieuView = new TextView(context);
        this.lieuView.setText(lieu);

        //ajout a la partie droite de l'annonce
        tlde.addView(titreView);
        tlde.addView(s, ls);
        tlde.addView(lieuView);
        tlde.addView(de);

        //set de l'image
        this.imgb = new Button(context);
        this.setClickEvent(context);
        imgb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.poule, 0, 0, 0);

        //finalisation de la vue de l'annonce
        this.addView(imgb);
        this.addView(tlde);
    }

    private void setClickEvent(Context context) {
        this.imgb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detaille.class);
                context.startActivity(intent);
            }
        });
    }
}
