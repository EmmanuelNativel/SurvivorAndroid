package com.nativele.survivor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.Map;
import java.util.Set;

public class Score extends AppCompatActivity{

    private ListView mListView;
    private MyArrayAdaptater mArrayAdaptater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.score);

        // On récupere le SharedPreferences des scores
        SharedPreferences scorePrefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        //On récupère tous les couples (pseudo, score)
        Map<String, ?> resultatsGame = scorePrefs.getAll();
        int nbScore = resultatsGame.size();
        String[][] tableauScore = new String[nbScore][2];
        int indice = 0;

        //On remplit le tableau de score qui sera passer à l'ArrayAdapter.
        //indice 0 : pseudo, indice 1 : score
        for(Map.Entry<String, ?> entry : resultatsGame.entrySet()) {

            String pseudo = entry.getKey();
            String score = entry.getValue().toString();
            tableauScore[indice][0] = pseudo;
            tableauScore[indice][1] = score;

            indice++;
        }

        /*
        * Passage du tableau dans l'adaptateur
        * pour l'affichage dans la ListView.
        * */
        mListView = (ListView)findViewById(R.id.ListScore);
        mArrayAdaptater = new MyArrayAdaptater(this,tableauScore);
        mListView.setAdapter(mArrayAdaptater);

        System.out.println(tableauScore);
    }

    public void backToMenu(android.view.View view){
        Intent intent = new Intent(view.getContext(), Menu.class);
        startActivity(intent);
        finish();
    }
}
