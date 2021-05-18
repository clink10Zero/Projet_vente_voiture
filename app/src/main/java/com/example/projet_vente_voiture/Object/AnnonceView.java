package com.example.projet_vente_voiture.Object;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.projet_vente_voiture.Activity.Detaille;
import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.PhotoBD;
import com.example.projet_vente_voiture.R;

import java.util.Date;
import java.util.List;

public class AnnonceView extends LinearLayout {

    private TextView dateView;
    private TextView prixView;
    private TextView titreView;
    private TextView lieuView;
    private Button imgb;
    private ImageView img;

    /*public AnnonceView(Context context, String date, int prix, String titre, String lieu) {
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
        this.setClickEvent(context,id);
        imgb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.poule, 0, 0, 0);

        //finalisation de la vue de l'annonce
        this.addView(imgb);
        this.addView(tlde);
    }*/

    public AnnonceView(Context context, Annonce annonce, int currentUserId) {
        super(context);

        LinearLayout de = new LinearLayout(context);
        de.setOrientation(LinearLayout.HORIZONTAL);

        this.dateView = new TextView(context);
        this.dateView.setText(annonce.getDate());

        this.prixView = new TextView(context);
        this.prixView.setText("prix : " + annonce.getPrix() + " €");

        //ajout a la vue
        de.addView(dateView);
        de.addView(prixView);

        //set TITRE et LIEU
        LinearLayout tlde = new LinearLayout(context);
        tlde.setOrientation(LinearLayout.VERTICAL);
        this.titreView = new TextView(context);
        this.titreView.setText(annonce.getTitre());
        Space s = new Space(context);
        LinearLayout.LayoutParams ls = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        this.lieuView = new TextView(context);
        this.lieuView.setText(annonce.getLieu());

        //ajout a la partie droite de l'annonce
        tlde.addView(titreView);
        tlde.addView(s, ls);
        tlde.addView(lieuView);
        tlde.addView(de);
        //TODO en cours
       /* PhotoBD PBD = new PhotoBD(context);
        int id_annonce = annonce.getId();
        List<Photo> photos = PBD.getPhotosByAnnonceId(id_annonce);
        if(photos!=null) {
            String chemin = photos.get(0).getChemin();
            Bitmap bitmap = BitmapFactory.decodeFile(chemin);

            this.img = new ImageView(context);
            this.setClickEvent(context);

            if (bitmap != null) {
                Bitmap photoBitmap = makeMiniBitmap(BitmapFactory.decodeFile(chemin), 8);
                img.setImageBitmap(photoBitmap);
            }
        }else {*/

            //set de l'image
            this.imgb = new Button(context);
            this.setClickEvent(context,annonce.getId(),currentUserId);
            imgb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.poule, 0, 0, 0);
       // }
        //finalisation de la vue de l'annonce
        this.addView(imgb);
        this.addView(tlde);
    }

    private void setClickEvent(Context context, int id, int currentUserId) {
        this.imgb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnonceBD ABD = new AnnonceBD(context);
                Annonce annonce = ABD.getAnnonceById(id);
                if(annonce.getId_auteur()!=currentUserId){
                    annonce.setVu(annonce.getVu()+1);
                    ABD.updateAnnonce(id,annonce);
                }
                Intent intent = new Intent(context, Detaille.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

    private static Bitmap makeMiniBitmap(Bitmap bitmap, int diviseur){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/diviseur,bitmap.getHeight()/diviseur,false);
    }
}
