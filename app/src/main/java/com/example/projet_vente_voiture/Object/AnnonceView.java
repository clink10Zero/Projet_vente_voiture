package com.example.projet_vente_voiture.Object;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.projet_vente_voiture.Activity.Detaille;
import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.PhotoBD;
import com.example.projet_vente_voiture.R;

import java.util.List;

public class AnnonceView extends LinearLayout {

    private TextView dateView;
    private TextView prixView;
    private TextView titreView;
    private TextView lieuView;
    private ImageView img;

    public AnnonceView(Activity activity, Annonce annonce, int currentUserId) {
        super(activity);

        LinearLayout de = new LinearLayout(activity);
        de.setOrientation(LinearLayout.HORIZONTAL);

        this.dateView = new TextView(activity);
        this.dateView.setText(annonce.getDate());

        this.prixView = new TextView(activity);
        this.prixView.setText("prix : " + annonce.getPrix() + " €");

        //ajout a la vue
        de.addView(dateView);
        de.addView(prixView);

        //set TITRE et LIEU
        LinearLayout tlde = new LinearLayout(activity);
        tlde.setOrientation(LinearLayout.VERTICAL);
        this.titreView = new TextView(activity);
        this.titreView.setText(annonce.getTitre());
        Space s = new Space(activity);
        LinearLayout.LayoutParams ls = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        this.lieuView = new TextView(activity);
        this.lieuView.setText(annonce.getLieu());

        //ajout a la partie droite de l'annonce
        tlde.addView(titreView);
        tlde.addView(s, ls);
        tlde.addView(lieuView);
        tlde.addView(de);

        //TODO redimmenssioner

        img = new ImageView(activity);

        int height = (getResources().getDrawable(R.drawable.poule)).getMinimumHeight();
        int width = (getResources().getDrawable(R.drawable.poule)).getMinimumWidth();

        PhotoBD PBD = new PhotoBD(activity);
        int id_annonce = annonce.getId();
        List<Photo> photos = PBD.getPhotosByAnnonceId(id_annonce);
        if(photos!=null) {
            String chemin = photos.get(0).getChemin();
            Bitmap bitmap = BitmapFactory.decodeFile(chemin);

            if (bitmap != null) {
                Bitmap photoBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(chemin),width,height,true);
                img.setImageBitmap(photoBitmap);
            }
        }else {
            img.setImageDrawable(getResources().getDrawable(R.drawable.poule));
        }

        img.setPadding(50,50,50,50);
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnonceBD ABD = new AnnonceBD(activity);
                Annonce annonce = ABD.getAnnonceById(id_annonce);
                if(annonce.getId_auteur()!=currentUserId){
                    annonce.setVu(annonce.getVu()+1);
                    ABD.updateAnnonce(id_annonce,annonce);
                }
                Intent intent = new Intent(activity, Detaille.class);
                intent.putExtra("id",id_annonce);
                activity.startActivity(intent);
            }
        });
        //finalisation de la vue de l'annonce
        this.addView(img);
        this.addView(tlde);
    }
}
