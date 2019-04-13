package com.nativele.survivor;

/*
 *
 * Ecran du menu
 *
 * */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
    }

    //Lancement du jeu
    public void lunchGameScene(android.view.View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        startActivity(intent);
    }

    //lancement de la liste des scores
    public void lunchScoreScreen(android.view.View view){
        Intent intent = new Intent(view.getContext(), Score.class);
        startActivity(intent);
    }
}
