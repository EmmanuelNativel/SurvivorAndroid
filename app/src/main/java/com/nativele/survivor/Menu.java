package com.nativele.survivor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Menu extends Activity {

    //Button btMenuJouer;
    //Button btMenuScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.menu);
        /*
        btMenuJouer = findViewById(R.id.BtJouer);
        btMenuScore = findViewById(R.id.BtScore);


        btMenuJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btMenuScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Score.class);
                startActivity(intent);
            }
        }); */
    }

    public void lunchGameScene(android.view.View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        startActivity(intent);
    }

    public void lunchScoreSceen(android.view.View view){
        Intent intent = new Intent(view.getContext(), Score.class);
        startActivity(intent);
    }

}
