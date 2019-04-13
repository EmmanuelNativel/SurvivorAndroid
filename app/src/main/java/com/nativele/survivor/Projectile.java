package com.nativele.survivor;

/*
 * Classe Projectile
 *
 * */

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

        //Chargement des images pour l'animation en fonction de la direction souhaitée

        int id = Constants.CURRENT_CONTEXT.getResources().getIdentifier("projectile_"+this.direction+"_0", "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        Bitmap move0 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);

        id = Constants.CURRENT_CONTEXT.getResources().getIdentifier("projectile_"+this.direction+"_1", "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        Bitmap move1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);

        //Création de l'animation
        this.move = new Animation(new Bitmap[]{move0, move1}, 0.5f, true);
    }

    /*
     * Déplace le projectile dans la bonne direction
     */
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

        //Si le projectile sort de l'écran, on le détruit
        if(this.rectangle.centerX() < 0 || this.rectangle.centerX() > Constants.SCREEN_WIDTH){
            this.toDestroy = true;
        }
    }

    //Accesseurs
    public Rect getRectangle(){ return rectangle; }
    public boolean isToDestroy(){ return toDestroy; }
    public void setToDestroy(boolean destroy){  this.toDestroy = destroy; }
}
