package com.nativele.survivor;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    //AnimationStore animationStore = new AnimationStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Constants.SCREEN_WIDTH = this.getWindowManager().getDefaultDisplay().getWidth();
        Constants.SCREEN_HEIGHT = this.getWindowManager().getDefaultDisplay().getHeight();
        //loadImages();
        //Constants.ANIMATIONS = animationStore;
        setContentView(new GameScene(this));

    }

    /*
    public void loadImages(){
        BitmapFactory bitmapFactory = new BitmapFactory();

        //Animation Marcher :
        Bitmap[] Img = new Bitmap[24];

        for(int i=0; i<24 ; i++){
            String numImg = String.format("%03d", i);
            int id = this.getResources().getIdentifier("golem_move_right_" + numImg, "drawable", this.getPackageName());
            Img[i] = bitmapFactory.decodeResource(this.getResources(), id);
        }

        animationStore.golem_move_right = new Animation(Img, 0.5f, true);

        //Animation Mourir :
        Img = new Bitmap[15];

        for(int i=0; i<15 ; i++){
            String numImg = String.format("%03d", i);
            int id = this.getResources().getIdentifier("golem_die_right_" + numImg, "drawable", this.getPackageName());
            Img[i] = bitmapFactory.decodeResource(this.getResources(), id);
        }

        animationStore.golem_die_right = new Animation(Img, 2, false);

        //Animation Attaquer :
        Img = new Bitmap[12];

        for(int i=0; i<12 ; i++){
            String numImg = String.format("%03d", i);
            int id = this.getResources().getIdentifier("golem_attack_right_" + numImg, "drawable", this.getPackageName());
            Img[i] = bitmapFactory.decodeResource(this.getResources(), id);
        }

        animationStore.golem_attack_right = new Animation(Img, 2, false);


        //animationManager = new AnimationManager(new Animation[]{ move_Right, attack_Right, die_Right});
    }*/
}
