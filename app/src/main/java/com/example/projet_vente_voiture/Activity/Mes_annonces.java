package com.example.projet_vente_voiture.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceView;
import com.example.projet_vente_voiture.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Mes_annonces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces);

        int currentUserId = getIntent().getIntExtra("currentUser",-1);
        if(currentUserId==-1){
            finish();
        }

        AnnonceBD ABD = new AnnonceBD(this);
        List<Annonce> annonces = ABD.getAnnoncesByUserId(currentUserId);
        if(annonces!=null){
            LinearLayout ll_dynamic = findViewById(R.id.dynamique_linear_layout_mes_annonces);
            for(Annonce a : annonces){
                AnnonceView view = new AnnonceView(this,a,currentUserId);
                ll_dynamic.addView(view);
            }
        }

        Button btn_nouvelle_annonce = findViewById(R.id.button_nouvelle_annonce_mes_annonces);
        btn_nouvelle_annonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Ajouter_Annonce.class);
                intent.putExtra("currentUser",currentUserId);
                startActivity(intent);
            }
        });
    }
}