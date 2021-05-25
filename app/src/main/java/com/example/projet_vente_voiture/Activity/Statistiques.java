package com.example.projet_vente_voiture.Activity;

import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.AnnonceSauvegardeBD;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.AnnonceSauvegarde;
import com.example.projet_vente_voiture.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Statistiques extends General {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);
        setSupportActionBar(findViewById(R.id.toolbar));

        AnnonceBD ABD = new AnnonceBD(this);
        AnnonceSauvegardeBD ASBD= new AnnonceSauvegardeBD(this);
        List<Annonce> annonceList = ABD.getAnnoncesByUserId(currentUserId);
        LinearLayout dynamic = (findViewById(R.id.layout_stat_by_annonce));
        if(annonceList!=null){

            int max=0;
            int count=0;
            for (Annonce a : annonceList){
                if(a.getVu()>max){
                    max=a.getVu();
                }
                count+=a.getVu();

                LinearLayout statAnnonce = new LinearLayout(this);
                statAnnonce.setOrientation(LinearLayout.HORIZONTAL);
                TextView nom = new TextView(this);
                nom.setText(a.getTitre());
                TextView nbVue = new TextView(this);
                nbVue.setText(a.getVu()+"");
                TextView nbabo = new TextView(this);
                ArrayList<AnnonceSauvegarde> as =  ASBD.getAnnonceSauvegardeByAnnonceId(a.getId());
                if(as!=null){
                    nbabo.setText(""+as.size());
                }
                else{
                    nbabo.setText("0");
                }
                statAnnonce.addView(nom);
                statAnnonce.addView(nbVue);
                statAnnonce.addView(nbabo);

                dynamic.addView(statAnnonce);

            }

            ((TextView)(findViewById(R.id.text_view_nb_annonce_stats))).setText(""+annonceList.size());
            ((TextView)(findViewById(R.id.text_view_max_vu_stats))).setText(""+max);
            ((TextView)(findViewById(R.id.text_view_total_vu_stats))).setText(""+count);
        }
    }
}