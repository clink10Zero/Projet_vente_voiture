package com.example.projet_vente_voiture.PopUp;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

import com.example.projet_vente_voiture.R;

public class ConfirmPopUp extends Dialog {

    private String title;
    private final Button confirmButton;
    private final Button cancelButton;
    private final TextView titleView;

    public ConfirmPopUp(Activity activity){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.confirm_pop_up_template);
        this.title = "Titre";
        this.confirmButton = findViewById(R.id.btnValider);
        this.cancelButton = findViewById(R.id.btnAnnuler);
        this.titleView = findViewById(R.id.title);
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
        titleView.setTextSize(25);
    }
}