package com.example.projet_vente_voiture.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.R;

import java.util.List;

public class Statistiques extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);
        setSupportActionBar(findViewById(R.id.toolbar));

        AnnonceBD ABD = new AnnonceBD(this);
        List<Annonce> annonceList = ABD.getAnnoncesByUserId(currentUserId);
        if(annonceList!=null){

            int max=0;
            int count=0;
            for (Annonce a : annonceList){
                if(a.getVu()>max){
                    max=a.getVu();
                }
                count+=a.getVu();
            }

           ((TextView)(findViewById(R.id.text_view_nb_annonce_stats))).setText(""+annonceList.size());
            ((TextView)(findViewById(R.id.text_view_max_vu_stats))).setText(""+max);
            ((TextView)(findViewById(R.id.text_view_total_vu_stats))).setText(""+count);
        }
    }
}