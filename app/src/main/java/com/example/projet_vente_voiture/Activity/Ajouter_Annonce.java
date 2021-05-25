package com.example.projet_vente_voiture.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_vente_voiture.BD.AnnonceBD;
import com.example.projet_vente_voiture.BD.CritereAnnonceBD;
import com.example.projet_vente_voiture.BD.CritereBD;
import com.example.projet_vente_voiture.BD.PhotoBD;
import com.example.projet_vente_voiture.BD.ValeurCritereBD;
import com.example.projet_vente_voiture.Helper;
import com.example.projet_vente_voiture.MyApp;
import com.example.projet_vente_voiture.PopUp.AddPhotoConfirmPopUp;
import com.example.projet_vente_voiture.Object.Annonce;
import com.example.projet_vente_voiture.PopUp.ConfirmPopUp;
import com.example.projet_vente_voiture.Object.Critere;
import com.example.projet_vente_voiture.Object.CritereAnnonce;
import com.example.projet_vente_voiture.Object.Photo;
import com.example.projet_vente_voiture.Object.ResultatForm;
import com.example.projet_vente_voiture.Object.ValeurCritere;
import com.example.projet_vente_voiture.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import static android.view.View.GONE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.CRITERE_PREDEF;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.HEURE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.JOUR;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.MOIS;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.NO;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.SEMAINE;
import static com.example.projet_vente_voiture.BD.MaBaseSQLite.YES;

public class Ajouter_Annonce extends General {

    public static final int PICK_IMAGE =1;
    private static final int REQUEST_CAPTURE_IMAGE =2;
    int currentAnnonceId;
    List<Photo> currentPhotoList;
    List<Photo> bdPhotoList;
    int location;
    int temps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);
        setSupportActionBar(findViewById(R.id.toolbar));

        this.currentAnnonceId = getIntent().getIntExtra("currentAnnonce",-1);

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
                valeur.setWidth(Helper.getScreenWidth(this)-tv_nom_critere.getWidth());
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
        RadioGroup radioGroupLocation = findViewById(R.id.radio_group_location);

        LinearLayout ll_temps = findViewById(R.id.ll_temps_ajouter_annonce);
        ll_temps.setVisibility(GONE);
        if(currentAnnonceId!=-1){
            assert currentAnnonce != null;
            et_titre.setText(currentAnnonce.getTitre());
            et_lieu.setText(currentAnnonce.getLieu());
            et_description.setText(currentAnnonce.getDescritpion());
            et_prix.setText(currentAnnonce.getPrix()+"");
            if(currentAnnonce.getLocation()==YES){
                ll_temps.setVisibility(View.VISIBLE);
                radioGroupLocation.check(R.id.radio_button_location_ajouter_annonce);
                location=YES;
            }
            else{
                radioGroupLocation.check(R.id.radio_button_vente_ajouter_annonce);
                location=NO;
            }
        }

        Ajouter_Annonce activity = this;
        radioGroupLocation.setOnCheckedChangeListener(new  RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LinearLayout ll_temps = findViewById(R.id.ll_temps_ajouter_annonce);
                if(checkedId==R.id.radio_button_vente_ajouter_annonce){
                    ll_temps.setVisibility(GONE);
                    location=NO;
                }
                else{
                    ll_temps.setVisibility(View.VISIBLE);
                    location=YES;
                }

            }
        });


        Button btn_validation = findViewById(R.id.button_validation_ajouter_annonce);
        btn_validation.setOnClickListener(v -> {
            //check contents
            ResultatForm test = isFormOK(et_titre, et_description, et_lieu, et_prix);
            if (!test.getBool()) {
                Toast.makeText(getApplicationContext(), test.getText(), Toast.LENGTH_LONG).show();
            }
            else{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date d = new Date();
                String date = formatter.format(d);

                if(location==YES){
                    switch (((RadioGroup)findViewById(R.id.radio_group_temps_ajouter_annonce)).getCheckedRadioButtonId()){
                        case R.id.radio_button_jour_ajouter_annone:
                            temps=JOUR;
                            break;
                        case R.id.radio_button_semaine_ajouter_annone:
                            temps=SEMAINE;
                            break;
                        case  R.id.radio_button_mois_ajouter_annonce:
                            temps=MOIS;
                            break;
                        default:
                            temps=HEURE;
                    }
                }

                //Annonce
                Annonce nouvelle_annonce;
                if(currentAnnonceId!=-1) {
                    Annonce original = ABD.getAnnonceById(currentAnnonceId);
                    nouvelle_annonce = new Annonce(currentUserId,et_titre.getText().toString(),et_description.getText().toString(),et_lieu.getText().toString(),Integer.parseInt(et_prix.getText().toString()),original.getDate(),original.getVu(),original.getPromotion(), location,temps);
                    ABD.updateAnnonce(currentAnnonceId,nouvelle_annonce);
                }
                else{
                    nouvelle_annonce = new Annonce(currentUserId,et_titre.getText().toString(),et_description.getText().toString(),et_lieu.getText().toString(),Integer.parseInt(et_prix.getText().toString()),date,0,NO, location,temps);
                    ABD.insertAnnonce(nouvelle_annonce);
                    currentAnnonceId=nouvelle_annonce.getId();
                }

                //Critères
                //TODO maybe la suppression de critere ?
                for(int i=0;i<et_list.size();i++){
                    if(!et_list.get(i).getText().toString().equals("")){
                        CritereAnnonce nouveau_critere_annonce = new CritereAnnonce(critere_list.get(i).getId(),currentAnnonceId,et_list.get(i).getText().toString());
                        CritereAnnonce ca= CABD.getCritereAnnonceByAnnonceAndCritereId(currentAnnonceId,critere_list.get(i).getId());
                        if(ca!=null){
                            CABD.updateCritereAnnonce(ca.getId(),nouveau_critere_annonce);
                        }else{
                            CABD.insertCritereAnnonce(nouveau_critere_annonce);
                        }
                    }
                }

                //Photos
                PhotoBD PBD = new PhotoBD(getApplicationContext());
                bdPhotoList=PBD.getPhotosByAnnonceId(currentAnnonceId);

                //Ajout dans la bd
                List<Photo> toAddPhotoList = new ArrayList<>();
                if(bdPhotoList!=null){
                    if(currentPhotoList!=null){
                        for(Photo p : currentPhotoList){
                            boolean alreadyIn = false;
                            int i=0;
                            while(!alreadyIn && i<bdPhotoList.size()){
                                if(bdPhotoList.get(i).getChemin().equals(p.getChemin())) {
                                    alreadyIn = true;
                                }
                                i++;
                            }
                            if(!alreadyIn){
                                toAddPhotoList.add(p);
                            }
                        }
                    }
                }
                else{
                    if(currentPhotoList!=null){
                        toAddPhotoList.addAll(currentPhotoList);
                    }
                }

                if(!toAddPhotoList.isEmpty()){
                    for(Photo p : toAddPhotoList){
                        p.setId_annonce(currentAnnonceId);
                        PBD.insertPhoto(p);
                    }
                }

                //Suppression de la bd
                List<Photo> toDeletePhotoList = new ArrayList<>();
                if (currentPhotoList!=null && !currentPhotoList.isEmpty()){
                    if(bdPhotoList!=null){
                        for(Photo p : bdPhotoList){
                            int i=0;
                            boolean isInCurrent = false;
                            while(!isInCurrent && i<currentPhotoList.size()){
                                if(currentPhotoList.get(i).getChemin().equals(p.getChemin())){
                                    isInCurrent=true;
                                }
                                i++;
                            }
                            if(!isInCurrent) {
                                toDeletePhotoList.add(p);
                            }
                        }
                    }
                }
                else{
                    if(bdPhotoList!=null){
                        toDeletePhotoList.addAll(bdPhotoList);
                    }
                }
                if(!toDeletePhotoList.isEmpty()){
                    for(Photo p : toDeletePhotoList){
                        PBD.removePhotoWithID(p.getId());
                    }
                }

                //Redirection
                Intent intent = new Intent(getApplicationContext(),Detaille.class);
                intent.putExtra("id",nouvelle_annonce.getId());
                startActivity(intent);
                finish();
            }
        });

        PhotoBD PDB = new PhotoBD(this);
        currentPhotoList = null;
        bdPhotoList = null;
        if(currentAnnonceId!=-1){
            bdPhotoList = PDB.getPhotosByAnnonceId(currentAnnonceId);
            currentPhotoList = bdPhotoList;
            displayPhotos(findViewById(R.id.table_photo_ajouter_annonce));
        }

        Button btn_parcourir = findViewById(R.id.button_parcourir_photo_ajouter_annonce);
        btn_parcourir.setOnClickListener(view -> {
            int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE);
            }
            else{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        Button btn_prendre_photo = findViewById(R.id.button_prendre_photo_ajouter_annonce);
        btn_prendre_photo.setOnClickListener(view -> openCameraIntent());
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

    public void displayPhotos(TableLayout photoTable) {
        if (this.currentPhotoList != null) {
            photoTable.removeAllViews();
            for (int i = 0; i < currentPhotoList.size(); i++) {
                final Photo currentPhoto = currentPhotoList.get(i);
                Bitmap bitmap = BitmapFactory.decodeFile(currentPhoto.getChemin());
                if (bitmap == null) {
                    currentPhotoList.remove(currentPhoto);
                    Toast.makeText(this, "Certaines photos ont été supprimés de la galerie et ne sont donc plus disponibles", Toast.LENGTH_SHORT).show();
                } else {
                    ImageView imageView = new ImageView(this);
                    final String picturePath = currentPhoto.getChemin();
                    Bitmap photoBitmap = Helper.makeMiniBitmap(BitmapFactory.decodeFile(picturePath), 8);

                    imageView.setImageBitmap(photoBitmap);
                    TextView textView = new TextView(this);
                    textView.setText(currentPhoto.getChemin());
                    textView.setVisibility(GONE);

                    Activity activity=this;
                    Button deleteButton = new Button(this);
                    deleteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.photo_delete, 0, 0, 0);
                    deleteButton.setPadding(10, 10, 0, 0);
                    deleteButton.setBackgroundColor(0);
                    deleteButton.setOnClickListener(view -> {
                        final ConfirmPopUp confirmPopUp = new ConfirmPopUp(activity);
                        confirmPopUp.setTitle("Confirmer suppression ?");
                        confirmPopUp.getConfirmButton().setOnClickListener(view1 -> {
                            confirmPopUp.dismiss();
                            currentPhotoList.remove(currentPhoto);
                            displayPhotos(photoTable);
                        });
                        confirmPopUp.getCancelButton().setOnClickListener(view12 -> confirmPopUp.dismiss());
                        confirmPopUp.build();
                    });

                    ConstraintLayout constraintLayout = new ConstraintLayout(activity);
                    constraintLayout.addView(imageView, 0);
                    constraintLayout.addView(deleteButton, 1);
                    constraintLayout.setMinHeight(photoBitmap.getHeight());
                    constraintLayout.setMaxWidth(photoBitmap.getWidth());
                    constraintLayout.addView(textView, 2);
                    photoTable.addView(constraintLayout);

                    Space space = new Space(activity);
                    space.setMinimumHeight(6);
                    photoTable.addView(space);
                }
            }
            photoTable.setPadding(10, 0, 10, 0);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        activityResult(this,resultCode,requestCode,data);
    }

    public void activityResult(final Activity activity, int resultCode, int requestCode, @Nullable Intent data) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {//retour de galerie
                Uri selectedImage = null;
                if (data != null) {
                    selectedImage = data.getData();
                }
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = null;
                if (selectedImage != null) {
                    String sel = MediaStore.Images.Media._ID + "=?";

                    String wholeID = DocumentsContract.getDocumentId(selectedImage);

                    String id = wholeID.split(":")[1];
                    cursor = activity.getContentResolver().
                            query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    filePathColumn, sel, new String[]{id}, null);
                }
                if (cursor != null) {
                    if (cursor.moveToFirst()) {

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        final String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        final AddPhotoConfirmPopUp addPhotoConfirmPopUp = new AddPhotoConfirmPopUp(activity);
                        addPhotoConfirmPopUp.getImageView().setImageBitmap(Helper.makeMiniBitmap(BitmapFactory.decodeFile(picturePath), 6));
                        addPhotoConfirmPopUp.getConfirmButton().setOnClickListener(view -> {
                            addPhotoConfirmPopUp.dismiss();
                            insertPhotoInActivity(picturePath);
                        });
                        addPhotoConfirmPopUp.getCancelButton().setOnClickListener(view -> {
                            addPhotoConfirmPopUp.dismiss();
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                        });
                        addPhotoConfirmPopUp.build();
                    } else {
                        Toast.makeText(activity, "Rester sur 'Images' pour que cela fonctionne, désolé on n'a pas trouvé pourquoi", Toast.LENGTH_LONG).show();
                    }
                }
            }
            if (requestCode == REQUEST_CAPTURE_IMAGE) {//retour d'appareil photo
                File[] filePhotoList = Objects.requireNonNull(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)).listFiles();//photos take by the app
                File src = filePhotoList[filePhotoList.length - 1];

                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/VenteVoitures";
                String timeStamp =
                        new android.icu.text.SimpleDateFormat("yyyyMMdd_HHmmss",
                                Locale.getDefault()).format(new Date());
                String imageFileName = "IMG_" + timeStamp + ".jpg";
                File fileDirectory = new File(directory);
                if (!fileDirectory.exists()) {
                    fileDirectory.mkdirs();
                }
                File dest = new File(directory + "/" + imageFileName);

                try {
                    Helper.copy(src, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                src.delete();

                insertPhotoInActivity(directory + "/" + imageFileName);
            }
        }
    }

    private void insertPhotoInActivity(String picturePath) {
        Photo photoToInsert = new Photo(currentAnnonceId,picturePath);

        if (currentPhotoList == null) {
            currentPhotoList = new ArrayList<>();
        }
        boolean isPhotoAlreadyExistInObject = false;
        for (int i = 0; i < currentPhotoList.size(); i++) {
            if (currentPhotoList.get(i).getChemin().equals(photoToInsert.getChemin())) {
                isPhotoAlreadyExistInObject = true;
            }
        }
        if (!isPhotoAlreadyExistInObject) {
            currentPhotoList.add(photoToInsert);
        } else {
            Toast.makeText(getApplicationContext(), "Photo déjà présente", Toast.LENGTH_SHORT).show();
        }

        displayPhotos(findViewById(R.id.table_photo_ajouter_annonce));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_CAPTURE_IMAGE:
                    openCameraIntent();
                    break;
                case PICK_IMAGE:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    break;
            }
        }
        else {
            Intent intent = new Intent(this, getClass());
            intent.putExtra("id",currentAnnonceId);
            startActivity(intent);
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.projet_vente_voiture.fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent,
                        REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new android.icu.text.SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}