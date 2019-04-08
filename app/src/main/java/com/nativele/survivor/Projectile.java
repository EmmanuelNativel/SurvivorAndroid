package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Projectile implements Sprite {

    private Rect rectangle;

    public static final int MOVE_RIGHT = 0;
    public static final int MOVE_LEFT = 1;
    public int state;

    private Animation move_Right, move_Left;
    private AnimationManager animationManager;
    private int speed;
    private String direction;
    public boolean toDestroy;


    public Projectile(Rect rectangle, String direction){

        this.rectangle = rectangle;
        this.speed = 50;
        this.direction = direction;
        this.toDestroy = false;

        BitmapFactory bitmapFactory = new BitmapFactory();

        Bitmap moveRight0 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.projectile_right_0);
        Bitmap moveRight1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.projectile_right_1);

        move_Right = new Animation(new Bitmap[]{moveRight0, moveRight1}, 0.5f, true);

        Bitmap moveLeft0 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.projectile_left_0);
        Bitmap moveLeft1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.projectile_left_1);

        move_Left = new Animation(new Bitmap[]{moveLeft0, moveLeft1}, 0.5f, true);

        animationManager = new AnimationManager(new Animation[]{move_Right, move_Left});
    }

    public void move(){
        int sens;
        if(direction.equals("RIGHT")){
            sens = 1;
            this.state = MOVE_RIGHT;
        } else {
            sens = -1;
            this.state = MOVE_LEFT;
        }

        int left = rectangle.left + speed*sens;
        int top = rectangle.top;
        int right = rectangle.right + speed*sens;
        int bottom = rectangle.bottom;

        this.rectangle.set(left, top, right, bottom);
    }


    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        move();
        animationManager.playAnim(state);
        animationManager.update();
        if(this.rectangle.centerX() < 0 || this.rectangle.centerX() > Constants.SCREEN_WIDTH){
            this.toDestroy = true;
        }
    }
}
