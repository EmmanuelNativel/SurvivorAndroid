package com.nativele.survivor;

/*
 * Ecran Game Over
 *
 * */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class GameOver extends Activity {

    private EditText inputPseudo;
    private TextView scoreLabel;
    private String score;
    private Calendar calendar;
    private Date date;
    private String strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_game_over);

        calendar = Calendar.getInstance();
        date = new Date(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        strDate = DateFormat.format("dd.MM.yyyy",date).toString();

        //Le score est récupéré dans l'Intent
        score = (String) getIntent().getSerializableExtra("score");

        scoreLabel = findViewById(R.id.ScoreLabel);
        scoreLabel.setText("Score : " + score);

        inputPseudo = findViewById(R.id.inputLabel);
        inputPseudo.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }


    public void onClickBtnValider(android.view.View view){
        String pseudo = inputPseudo.getText().toString().trim();
        if(!pseudo.equals("") && pseudo != null) { //Si le champ texte n'est pas vide

            //On stocke le score, le pseudo et la date dans le SharedPreferences
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            pseudo = pseudo +","+strDate;
            editor.putString(pseudo, score);
            editor.apply();

            //On passe à l'écran Menu
            Intent intent = new Intent(view.getContext(), Menu.class);
            startActivity(intent);
            finish();
        } else { //Si le champ est vide, on demande à le remplir
            inputPseudo.setHintTextColor(Color.RED);
            inputPseudo.getBackground().mutate().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
