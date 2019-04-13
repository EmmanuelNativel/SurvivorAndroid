package com.nativele.survivor;

/*
 * Ecran des Scores
 *
 * */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Score extends AppCompatActivity{

    private ListView mListView;
    private MyArrayAdaptater mArrayAdaptater;
    private List<String[]> donnee;
    SharedPreferences scorePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.score);

        // On récupere le SharedPreferences des scores
        scorePrefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        //On récupère tous les couples (pseudo, score)
        Map<String, ?> resultatsGame = scorePrefs.getAll();
        int nbScore = resultatsGame.size();
        String[][] tableauScore = new String[nbScore][3];
        int indice = 0;

        //On remplit le tableau de score qui sera passer à l'ArrayAdapter.
        //indice 0 : pseudo, indice 1 : score
        for(Map.Entry<String, ?> entry : resultatsGame.entrySet()) {

            String[] key= ((String)entry.getKey()).split(",");
            String pseudo = key[0];
            String date = key[1];
            String score = entry.getValue().toString();
            tableauScore[indice][0] = pseudo;
            tableauScore[indice][1] = score;
            tableauScore[indice][2] = date;

            indice++;
        }

        // Tri des elements du tableau;
        Arrays.sort(tableauScore, new java.util.Comparator<String[]>(){
            public int compare(String[] a, String[] b){
                return Integer.compare(Integer.parseInt(a[1]),Integer.parseInt(b[1]));
            }
        });

        // On reverse le tableau des scores pour l'affichage.
        for (int i=0; i < tableauScore.length/2;i++){
            String[] temp = tableauScore[i];
            tableauScore[i] = tableauScore[tableauScore.length-1 -i];
            tableauScore[tableauScore.length-1 -i]= temp;
        }

        donnee = Arrays.asList(tableauScore);

        /*
        * Passage du tableau dans l'adaptateur
        * pour l'affichage dans la ListView.
        * */
        mListView = (ListView)findViewById(R.id.ListScore);
        mArrayAdaptater = new MyArrayAdaptater(this,tableauScore);
        this.registerForContextMenu(mListView);
        mListView.setAdapter(mArrayAdaptater);

        System.out.println(tableauScore);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.action_delete:
                String[] key = donnee.get(menuInfo.position);
                String query = key[0]+","+key[2];
                scorePrefs.edit().remove(query).apply();
                System.out.println("Query : "+query);
                finish();
                startActivity(getIntent());
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void backToMenu(android.view.View view){
        Intent intent = new Intent(view.getContext(), Menu.class);
        startActivity(intent);
        finish();
    }
}
