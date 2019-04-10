package com.nativele.survivor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Set;

public class Score extends AppCompatActivity{

    private ListView mListView;
    private MyArrayAdaptater mArrayAdaptater;
    private SharedPreferences scorePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.score);

        scorePrefs = getSharedPreferences("names",MODE_PRIVATE);
        String[] names = scorePrefs.getString("names","").split(",");
        String[] scores = scorePrefs.getString("scores","").split(",");
        String[][] tableauScore = new String[names.length][2];

        if( names.length > 0 && scores.length > 0){
            for(int i=0; i< names.length; i++){
                tableauScore[i][0]= names[i];
                tableauScore[i][1] = scores[i];
                System.out.println(tableauScore[i][0]);
                System.out.println(tableauScore[i][1]);
            }
        }

        mListView = (ListView)findViewById(R.id.ListScore);
        mArrayAdaptater = new MyArrayAdaptater(this,tableauScore);
        mListView.setAdapter(mArrayAdaptater);

        System.out.println(tableauScore);
    }
}
