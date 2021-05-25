package com.example.projet_vente_voiture.PopUp;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet_vente_voiture.R;


public class AddPhotoConfirmPopUp extends Dialog {

    private String title;
    private final Button confirmButton;
    private final Button cancelButton;
    private final ImageView imageView;
    private final TextView titleView;

    public AddPhotoConfirmPopUp(Activity activity){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.add_photo_confirm_popup_template);
        this.imageView = findViewById(R.id.image);
        this.confirmButton = findViewById(R.id.btnValider);
        this.cancelButton = findViewById(R.id.btnAnnuler);
        this.titleView = findViewById(R.id.title);
        this.title = titleView.getText().toString();

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }


    public void build(){
        show();
        titleView.setText(title);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
