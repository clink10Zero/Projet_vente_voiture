package com.example.projet_vente_voiture;

import android.app.Application;

public class MyApp extends Application {
    private int currentUserId =-1;

    public int getCurrentUserId(){
        return currentUserId;
    }
    public void setUser(int id){
        this.currentUserId=id;
    }
}

