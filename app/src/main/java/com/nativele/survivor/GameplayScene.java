package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GameplayScene implements Scene {

    private Player player;
    private Bitmap background, ground;
    private Monster monster;

    public GameplayScene(){
        background = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(background, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);
        ground = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ground);
        ground = Bitmap.createScaledBitmap(ground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT/10, true);

        player = new Player(new Rect(Constants.SCREEN_WIDTH/2 - 100,Constants.SCREEN_HEIGHT - ground.getHeight() - 200, Constants.SCREEN_WIDTH/2 - 100 + 200,Constants.SCREEN_HEIGHT - ground.getHeight()));
        player.update();

        monster = new Monster(new Rect(0, Constants.SCREEN_HEIGHT - ground.getHeight() - 200, 200, Constants.SCREEN_HEIGHT - ground.getHeight() ));
    }

    /*
    public void reset(){
        clickPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(clickPoint);
    }
    */

    @Override
    public void update() {
        player.update();
        monster.update();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null); //background
        canvas.drawBitmap(ground, 0, Constants.SCREEN_HEIGHT - ground.getHeight(), null);  //ground
        player.draw(canvas);
        monster.draw(canvas);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :

                if(event.getX() > Constants.SCREEN_WIDTH/2){
                    player.attack("RIGHT");
                } else {
                    player.attack("LEFT");
                }

                

            //case MotionEvent.ACTION_MOVE :
                //playerPoint.set((int)event.getX(), (int)event.getY());

        }
    }
}
