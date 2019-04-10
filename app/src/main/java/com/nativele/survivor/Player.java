package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class Player implements Sprite {

    public static final int IDLE_RIGHT = 0;
    public static final int IDLE_LEFT = 1;
    public static final int ATTACK_RIGHT = 2;
    public static final int ATTACK_LEFT = 3;
    public static final int DIE = 4;

    private Rect rectangle;
    private Animation idle_Right, idle_Left, attack_Right, attack_Left, die;
    private AnimationManager animationManager;
    private ArrayList<Projectile> projectiles;
    private int state;
    private boolean isAlive, gameOver;

    public Player(Rect rectangle) {
        this.rectangle = rectangle;
        this.state = IDLE_RIGHT;
        this.projectiles = new ArrayList<>();
        this.isAlive = true;
        this.gameOver = false;

        BitmapFactory bitmapFactory = new BitmapFactory();

        Bitmap idleRight0 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_right_000);
        Bitmap idleRight1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_right_001);
        Bitmap idleRight2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_right_002);
        Bitmap idleRight3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_right_003);
        Bitmap idleRight4 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_right_004);

        idle_Right = new Animation(new Bitmap[]{idleRight0, idleRight1, idleRight2, idleRight3, idleRight4}, 0.5f, true);

        Bitmap idleLeft0 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_left_000);
        Bitmap idleLeft1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_left_001);
        Bitmap idleLeft2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_left_002);
        Bitmap idleLeft3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_left_003);
        Bitmap idleLeft4 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_idle_left_004);

        idle_Left = new Animation(new Bitmap[]{idleLeft0, idleLeft1, idleLeft2, idleLeft3, idleLeft4}, 0.5f, true);

        Bitmap attackRight0 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_right_000);
        Bitmap attackRight1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_right_001);
        Bitmap attackRight2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_right_002);
        Bitmap attackRight3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_right_003);
        Bitmap attackRight4 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_right_004);

        attack_Right = new Animation(new Bitmap[]{attackRight0, attackRight1, attackRight2, attackRight3, attackRight4}, 0.5f, false);

        Bitmap attackLeft0 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_left_000);
        Bitmap attackLeft1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_left_001);
        Bitmap attackLeft2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_left_002);
        Bitmap attackLeft3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_left_003);
        Bitmap attackLeft4 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_attack_left_004);

        attack_Left = new Animation(new Bitmap[]{attackLeft0, attackLeft1, attackLeft2, attackLeft3, attackLeft4}, 0.5f, false);

        Bitmap die0 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_die_000);
        Bitmap die1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_die_001);
        Bitmap die2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_die_002);
        Bitmap die3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_die_003);
        Bitmap die4 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_die_004);

        die = new Animation(new Bitmap[]{die0, die1, die2, die3, die4}, 0.5f, false);

        animationManager = new AnimationManager(new Animation[]{idle_Right, idle_Left, attack_Right, attack_Left, die});
    }

    public void die(){
        isAlive = false;
        state = DIE;
    }

    public void attack(String direction){
        if(isAlive) {
            int top = rectangle.centerY() - 80;
            int bottom = rectangle.centerY() + 80;
            int left, right;

            if (direction.equals("RIGHT")) {
                state = ATTACK_RIGHT;
                left = rectangle.centerX();
                right = left + 100;
            } else {
                state = ATTACK_LEFT;
                right = rectangle.centerX();
                left = right - 100;
            }

            projectiles.add(new Projectile(new Rect(left, top, right, bottom), direction));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);

        for(int i=0; i<projectiles.size(); i++){
            projectiles.get(i).draw(canvas);
        }
    }

    @Override
    public void update() {

        if(!isAlive && !animationManager.isAnimationPlaying(state)) {
            this.gameOver = true;
        }

        animationManager.playAnim(state);
        animationManager.update();
        if(state == ATTACK_RIGHT && !attack_Right.isPlaying()) { state = IDLE_RIGHT; }
        if(state == ATTACK_LEFT && !attack_Left.isPlaying()) { state = IDLE_LEFT; }

        for(int i=0; i<projectiles.size(); i++){
            projectiles.get(i).update();
            if(projectiles.get(i).isToDestroy()) projectiles.remove(i);
        }

    }

    //Accesseurs
    public ArrayList<Projectile> getProjectiles(){
        return projectiles;
    }
    public boolean isAlive(){ return isAlive; }
    public boolean isGameOver(){ return gameOver; }
    public Rect getRectangle() {
        return rectangle;
    }
}
