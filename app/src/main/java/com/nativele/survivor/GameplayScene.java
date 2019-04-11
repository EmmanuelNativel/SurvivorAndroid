package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GameplayScene implements Scene {

    private Player player;
    private Bitmap background, ground;
    private MonsterGenerator monsterGeneratorRight, monsterGeneratorLeft;
    private int score;
    private Paint scorePaint;

    //Paramètres pour espacer les tirs du personnage
    private long clickTime;
    private boolean isShootAllowed;
    private int shootTimeInterval;



    public GameplayScene(){
        this.background = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.background);
        this.background = Bitmap.createScaledBitmap(background, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);
        this.ground = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ground);
        this.ground = Bitmap.createScaledBitmap(ground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT/10, true);

        this.scorePaint = new Paint();
        this.scorePaint.setColor(Color.WHITE);
        this.scorePaint.setTextSize(70);
        this.scorePaint.setAntiAlias(true);

        this.player = new Player(new Rect(Constants.SCREEN_WIDTH/2 - 100,Constants.SCREEN_HEIGHT - ground.getHeight() - 200, Constants.SCREEN_WIDTH/2 - 100 + 200,Constants.SCREEN_HEIGHT - ground.getHeight()));

        this.monsterGeneratorRight = new MonsterGenerator(this, "right");
        this.monsterGeneratorLeft = new MonsterGenerator(this, "left");

        this.isShootAllowed = true;
        this.shootTimeInterval = 500; //ms
        this.score = 0;

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
        canvas.drawText("Score : " + this.score, Constants.SCREEN_WIDTH/2 - 100, 100, this.scorePaint );
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
    public int getScore(){ return score; }
    public void upgradeScore(int value){ this.score += value; }

}
