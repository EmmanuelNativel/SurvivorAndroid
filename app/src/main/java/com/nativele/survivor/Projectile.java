package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Projectile implements Sprite {

    private Rect rectangle;
    private Animation move;
    private int speed, sens;
    private String direction;
    private boolean toDestroy;


    public Projectile(Rect rectangle, String direction){

        this.rectangle = rectangle;
        this.speed = 50;
        this.direction = direction.equals("RIGHT") ? "right" : "left";
        this.toDestroy = false;
        if(direction.equals("RIGHT")){
            this.direction = "right";
            this.sens = 1;
        } else {
            this.direction = "left";
            this.sens = -1;
        }

        int id = Constants.CURRENT_CONTEXT.getResources().getIdentifier("projectile_"+this.direction+"_0", "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        Bitmap move0 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);

        id = Constants.CURRENT_CONTEXT.getResources().getIdentifier("projectile_"+this.direction+"_1", "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        Bitmap move1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);

        this.move = new Animation(new Bitmap[]{move0, move1}, 0.5f, true);
    }

    public void move(){

        int left = rectangle.left + speed*sens;
        int top = rectangle.top;
        int right = rectangle.right + speed*sens;
        int bottom = rectangle.bottom;

        this.rectangle.set(left, top, right, bottom);
    }


    @Override
    public void draw(Canvas canvas) {
        if(move.isPlaying()) move.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        move();
        if(!move.isPlaying()) move.play();
        if(move.isPlaying()) move.update();

        if(this.rectangle.centerX() < 0 || this.rectangle.centerX() > Constants.SCREEN_WIDTH){
            this.toDestroy = true;
        }
    }

    //Accesseurs
    public Rect getRectangle(){ return rectangle; }
    public boolean isToDestroy(){ return toDestroy; }
    public void setToDestroy(boolean destroy){  this.toDestroy = destroy; }
}
