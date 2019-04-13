package com.nativele.survivor;

/*
 * MyArrayAdapter : représente uen cellule de la liste des scores
 *
 * */

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdaptater extends ArrayAdapter {
    private final Context context;

    public MyArrayAdaptater(Context context, String[][] values){
        super(context, R.layout.cell_layout, values);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View cellView = convertView;
        if (cellView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = inflater.inflate(R.layout.cell_layout, parent, false);
        }

        TextView label = (TextView)cellView.findViewById(R.id.label);
        String[] donnee = (String[]) getItem(position);
        label.setText(" "+donnee[0]+" : "+donnee[1]+" Le : "+donnee[2]);

        return cellView;
    }
}
