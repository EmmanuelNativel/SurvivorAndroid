package com.nativele.survivor;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.Set;

public class Score extends AppCompatActivity{

    private ListView mListView;
    private MyArrayAdaptater mArrayAdaptater;
    // On initialise une varriable SharedPreferences pour simplifier les manipulations.
    private SharedPreferences scorePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.score);

        // On récupere le SharedPreferences des scores
        scorePrefs = getSharedPreferences("highscores",MODE_PRIVATE);
        /*
        * Edit des variables "names" et "scores" de SharedPreferences.
        * "names" porte les noms des scores dans l'ordre.
        * "scores" réciproquements.
        * Les deux variables sont stockées dans SharedPreferences
        * en tant que String pour faciliter leur enregistrements
        * car SharedPreferences n'accepte pas les tableaux.
        */
        scorePrefs.edit().putString("names","ARG,GRE,HOA,NAT").putString("scores","150,75,18,97").apply();
        String[] names = scorePrefs.getString("names","").split(",");
        String[] scores = scorePrefs.getString("scores","").split(",");

        /*
         * Construction du tableau passé à mArrayAdaptateur
         * à partir des tableau names et scores
         * names prendra la première place du tableau et score la deuxième.
         */

        String[][] tableauScore = new String[names.length][2];
        if( names.length > 0 && scores.length > 0){
            for(int i=0; i< names.length; i++){
                tableauScore[i][0]= names[i];
                tableauScore[i][1] = scores[i];
                System.out.println(tableauScore[i][0]);
                System.out.println(tableauScore[i][1]);
            }
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
}
