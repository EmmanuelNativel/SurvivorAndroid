package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GameplayScene implements Scene {

    private Player player;
    private Bitmap background, ground;
    private MonsterGenerator monsterGeneratorRight, monsterGeneratorLeft;

    //Paramètres pour espacer les tirs du personnage
    private long clickTime;
    private boolean isShootAllowed;
    private int shootTimeInterval;



    public GameplayScene(){
        background = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(background, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);
        ground = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ground);
        ground = Bitmap.createScaledBitmap(ground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT/10, true);

        player = new Player(new Rect(Constants.SCREEN_WIDTH/2 - 100,Constants.SCREEN_HEIGHT - ground.getHeight() - 200, Constants.SCREEN_WIDTH/2 - 100 + 200,Constants.SCREEN_HEIGHT - ground.getHeight()));

        monsterGeneratorRight = new MonsterGenerator(this, "right");
        monsterGeneratorLeft = new MonsterGenerator(this, "left");

        isShootAllowed = true;
        shootTimeInterval = 500; //ms

    }

    @Override
    public void update() {

        if(System.currentTimeMillis() - clickTime > shootTimeInterval) isShootAllowed = true;

        player.update();
        if(!player.isGameOver()) {
            monsterGeneratorRight.update();
            monsterGeneratorLeft.update();
        } else {
            monsterGeneratorRight.cleanMonsters();
            monsterGeneratorLeft.cleanMonsters();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null); //background
        canvas.drawBitmap(ground, 0, Constants.SCREEN_HEIGHT - ground.getHeight(), null);  //ground
        player.draw(canvas);
        monsterGeneratorRight.draw(canvas);
        monsterGeneratorLeft.draw(canvas);
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :

                if(event.getX() > Constants.SCREEN_WIDTH/2){

                    if(isShootAllowed) {
                        isShootAllowed = false;
                        clickTime = System.currentTimeMillis();
                        player.attack("RIGHT");
                    }
                } else {

                    if(isShootAllowed) {
                        isShootAllowed = false;
                        clickTime = System.currentTimeMillis();
                        player.attack("LEFT");
                    }
                }
        }
    }

    public Player getPlayer(){ return player; }
    public Bitmap getGround(){ return ground; }

}
