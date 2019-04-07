package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Monster implements Sprite {

    public static final int MOVE_RIGHT = 0;
    public static final int MOVE_LEFT = 1;
    public static final int ATTACK_RIGHT = 2;
    public static final int ATTACK_LEFT = 3;
    public static final int DIE_RIGHT = 4;
    public static final int DIE_LEFT = 5;

    private Rect rectangle;
    private AnimationManager animationManager;
    private Animation move_Right, move_Left, attack_Right, attack_Left, die_right, die_left;
    public int state;

    public Monster(Rect rectangle){
        this.rectangle = rectangle;
        this.state = 0;

        BitmapFactory bitmapFactory = new BitmapFactory();

        Bitmap[] moveRightImg = new Bitmap[24];
        //Bitmap[] moveLeftImg = new Bitmap[24];

        for(int i=0; i<24 ; i++){

            String numImg = String.format("%03d", i);
            int id = Constants.CURRENT_CONTEXT.getResources().getIdentifier("golem_move_right_" + numImg, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
            moveRightImg[i] = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);

            //id = Constants.CURRENT_CONTEXT.getResources().getIdentifier("golem_move_left_" + numImg, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
            //moveLeftImg[i] = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);
        }

        move_Right = new Animation(moveRightImg, 0.5f, true);
        //move_Left = new Animation(moveLeftImg, 0.5f, true);

        animationManager = new AnimationManager(new Animation[]{ move_Right, /*move_Left/*, attack_Right, attack_Left, die_right, die_left*/});
    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animationManager.playAnim(state);
        animationManager.update();
    }
}
