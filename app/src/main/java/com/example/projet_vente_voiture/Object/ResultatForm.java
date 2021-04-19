package com.example.projet_vente_voiture.Object;

public class ResultatForm {
    boolean bool;
    String text;

    public ResultatForm(Boolean bool, String text) {
        this.bool = bool;
        this.text = text;
    }

    public boolean getBool() {
        return bool;
    }

    public String getText() {
        return text;
    }
}
