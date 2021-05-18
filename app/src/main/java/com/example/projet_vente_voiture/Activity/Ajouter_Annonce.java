package com.example.projet_vente_voiture.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.CritereAnnonceBD;
import com.example.projet_vente_voiture.BD.CritereBD;
import com.example.projet_vente_voiture.BD.ValeurCritereBD;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.Object.Critere;
import com.example.projet_vente_voiture.Object.CritereAnnonce;
import com.example.projet_vente_voiture.Object.ResultatForm;
import com.example.projet_vente_voiture.Object.ValeurCritere;
import com.example.projet_vente_voiture.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.projet_vente_voiture.BD.MaBaseSQLite.CRITERE_PREDEF;

public class Ajouter_Annonce extends General {

    int currentAnnonceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);
        setSupportActionBar(findViewById(R.id.toolbar));

        Intent intent = getIntent();
        this.currentAnnonceId = intent.getIntExtra("currentAnnonce",-1);

        if(currentUserId==-1){
            finish();
        }
        AnnonceBD ABD = new AnnonceBD(this);
        Annonce currentAnnonce = null;
        CritereAnnonceBD CABD = new CritereAnnonceBD(this);
        if(currentAnnonceId!=-1){
            currentAnnonce=ABD.getAnnonceById(currentAnnonceId);
        }

        ValeurCritereBD VCBD = new ValeurCritereBD(this);


        LinearLayout ll_critere = findViewById(R.id.dynamique_linear_layout_criteres_ajouter_annonce);
        CritereBD CBD = new CritereBD(this);
        List<Critere> critere_list = CBD.getAllCritere();
        List<EditText> et_list = new ArrayList<>();
        for (Critere critere : critere_list) {
            LinearLayout ligne = new LinearLayout(this);
            ligne.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv_nom_critere = new TextView(this);
            tv_nom_critere.setText(critere.getNom());
            ligne.addView(tv_nom_critere);

            if(critere.getType()==CRITERE_PREDEF){
                //TODO use the predef values
                List<ValeurCritere> vc_list = VCBD.getValeurCritereByCritere(critere.getId());
                AutoCompleteTextView valeur = new AutoCompleteTextView(this);
                valeur.setWidth(getScreenWidth(this)-tv_nom_critere.getWidth());
                if(vc_list!=null) {
                    List<String> stringList = new ArrayList<>();
                    for (ValeurCritere vc : vc_list) {
                        stringList.add(vc.getValeur());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, stringList);
                    valeur.setAdapter(adapter);
                }
                if(currentAnnonceId!=-1){
                    CritereAnnonce critereAnnonce = CABD.getCritereAnnonceByAnnonceAndCritereId(currentAnnonceId,critere.getId());
                    if (critereAnnonce != null) {
                        valeur.setText(critereAnnonce.getValeur());
                    }
                }
                ligne.addView(valeur);
                et_list.add(valeur);
            }
            else{
                EditText et_contenu = new EditText(this);
                if(currentAnnonceId!=-1){
                    CritereAnnonce critereAnnonce = CABD.getCritereAnnonceByAnnonceAndCritereId(currentAnnonceId,critere.getId());
                    if(critereAnnonce!=null){
                        et_contenu.setText(critereAnnonce.getValeur());
                    }
                }
                ligne.addView(et_contenu);
                et_list.add(et_contenu);
            }
            ll_critere.addView(ligne);
        }

        EditText et_titre = findViewById(R.id.edit_text_titre_ajouter_annonce);
        EditText et_lieu = findViewById(R.id.edit_text_lieu_ajouter_annonce);
        EditText et_description = findViewById(R.id.edit_text_description_ajouter_annonce);
        EditText et_prix = findViewById(R.id.edit_text_prix_ajouter_annonce);

        if(currentAnnonceId!=-1){
            et_titre.setText(currentAnnonce.getTitre());
            et_lieu.setText(currentAnnonce.getLieu());
            et_description.setText(currentAnnonce.getDescritpion());
            et_prix.setText(currentAnnonce.getPrix()+"");
        }

        Button btn_validation = findViewById(R.id.button_validation_ajouter_annonce);
        btn_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date d = new Date();
                String date = formatter.format(d);

                //check contents
                ResultatForm test = isFormOK(et_titre, et_description, et_lieu, et_prix);
                if (!test.getBool()) {
                    Toast.makeText(getApplicationContext(), test.getText(), Toast.LENGTH_LONG).show();
                }
                else{
                    Annonce nouvelle_annonce = new Annonce(currentUserId,et_titre.getText().toString(),et_description.getText().toString(),et_lieu.getText().toString(),Integer.parseInt(et_prix.getText().toString()),date,0);
                    AnnonceBD ABD = new AnnonceBD(getApplicationContext());
                    if(currentAnnonceId!=-1) {
                        ABD.updateAnnonce(currentAnnonceId,nouvelle_annonce);
                    }
                    else{
                        ABD.insertAnnonce(nouvelle_annonce);
                    }

                    //TODO maybe la suppression de critere ?
                    CritereAnnonceBD CABD = new CritereAnnonceBD(getApplicationContext());
                    for(int i=0;i<et_list.size();i++){
                        if(!et_list.get(i).getText().toString().equals("")){
                            CritereAnnonce nouveau_critere_annonce = new CritereAnnonce(critere_list.get(i).getId(),nouvelle_annonce.getId(),et_list.get(i).getText().toString());
                            CritereAnnonce ca=CABD.getCritereAnnonceByAnnonceAndCritereId(nouvelle_annonce.getId(),critere_list.get(i).getId());
                            if(ca!=null){
                                CABD.updateCritereAnnonce(ca.getId(),nouveau_critere_annonce);
                            }else{
                                CABD.insertCritereAnnonce(nouveau_critere_annonce);
                            }
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(),Detaille.class);
                    intent.putExtra("id",nouvelle_annonce.getId());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private ResultatForm isFormOK(EditText et_titre , EditText et_description , EditText et_lieu , EditText et_prix) {
        String text = "";
        boolean bool = false;
        if (et_titre.getText().toString().equals("")) {
            text = "L'annonce doit avoir un titre";
        }
        else {
            if (et_description.getText().toString().equals("")) {
                text = "L'annonce doit avoir une description";
            }
            else {
                if (et_lieu.getText().toString().equals("")) {
                    text = "L'annonce doit avoir une localisation";
                }
                else {
                    if (et_prix.getText().toString().equals("")) {
                        text = "L'annonce doit avoir un prix";
                    }
                }
            }
        }
        if(text.equals("")){
            bool=true;
        }
        return new ResultatForm(bool,text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.currentUserId!=((MyApp) getApplication()).getCurrentUserId()){
            if(currentAnnonceId==-1){//=ajout
                Intent intent = new Intent(getApplicationContext(), this.getClass());
                getApplicationContext().startActivity(intent);
            }
            finish();
        }


    }

    public static int getScreenWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}