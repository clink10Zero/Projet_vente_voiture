package com.example.projet_vente_voiture.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.CritereBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.Critere;
import com.example.projet_vente_voiture.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.CRITERE_PREDEF;

public class Ajouter_Annonce extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);

        LinearLayout ll_critere = findViewById(R.id.dynamique_linear_layout_criteres_ajouter_annonce);
        CritereBD CBD = new CritereBD(this);
        for (Critere critere : CBD.getAllCritere()) {
            LinearLayout ligne = new LinearLayout(this);
            ligne.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv_nom_critere = new TextView(this);
            tv_nom_critere.setText(critere.getNom());
            ligne.addView(tv_nom_critere);

            if(critere.getType()==CRITERE_PREDEF){

            }
            else{
                EditText et_contenu = new EditText(this);
                ligne.addView(et_contenu);
            }
            ll_critere.addView(ligne);
        }

        EditText et_titre = findViewById(R.id.edit_text_titre_ajouter_annonce);
        EditText et_lieu = findViewById(R.id.edit_text_lieu_ajouter_annonce);
        EditText et_description = findViewById(R.id.edit_text_description_ajouter_annonce);
        EditText et_prix = findViewById(R.id.edit_text_prix_ajouter_annonce);

        Button btn_validation = findViewById(R.id.button_validation_ajouter_annonce);
        btn_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_auteur = 1;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date d = new Date();
                String date = formatter.format(d);
                Annonce nouvelle_annonce = new Annonce(id_auteur,et_titre.getText().toString(),et_description.getText().toString(),et_lieu.getText().toString(),Integer.parseInt(et_prix.getText().toString()),date);
                AnnonceBD ABD = new AnnonceBD(getApplicationContext());
                ABD.insertAnnonce(nouvelle_annonce);
                Intent intent = new Intent(getApplicationContext(),Detaille.class);
                intent.putExtra("id",nouvelle_annonce.getId());
                startActivity(intent);
                finish();
            }
        });

    }
}