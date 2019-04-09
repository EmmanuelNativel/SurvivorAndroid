package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.stream.Collector;


public class Monster implements Sprite {

    public static final int MOVE = 0;
    public static final int ATTACK = 1;
    public static final int DIE = 2;
    private Rect rectangle;
    private AnimationManager animationManager;
    public Animation AnimMove, AnimAttack, AnimDie;
    public int state;
    private int speed, sens, pv;
    private boolean stop;
    private String direction;
    private GameplayScene scene;
    private MonsterGenerator source;

    public Monster(GameplayScene scene, MonsterGenerator source, Rect rectangle, String type, String direction, int pv){
        this.scene = scene;
        this.source = source;
        this.rectangle = rectangle;
        this.state = MOVE;
        this.direction = direction;
        this.speed = 5;
        this.sens = this.direction.equals("right") ? 1 : -1;
        this.pv = pv;

        BitmapFactory bitmapFactory = new BitmapFactory();

        //Animation Marcher :
        Bitmap[] Img = new Bitmap[4];

        for(int i=0; i<4 ; i++){
            String numImg = String.format("%03d", i);
            int idR = Constants.CURRENT_CONTEXT.getResources().getIdentifier(type+"_move_" +direction+"_"+numImg, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
            Img[i] = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), idR);
        }

        AnimMove = new Animation(Img, 0.5f, true);

        Img = new Bitmap[4];
        for(int i=0; i<4 ; i++){
            String numImg = String.format("%03d", i);
            int idR = Constants.CURRENT_CONTEXT.getResources().getIdentifier(type+"_attack_" +direction+"_"+numImg, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
            Img[i] = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), idR);
        }

        AnimAttack = new Animation(Img, 0.5f, false);


        Img = new Bitmap[5];
        for(int i=0; i<5 ; i++){
            String numImg = String.format("%03d", i);
            int idR = Constants.CURRENT_CONTEXT.getResources().getIdentifier(type+"_die_" +direction+"_"+numImg, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
            Img[i] = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), idR);
        }

        AnimDie = new Animation(Img, 1, false);


        animationManager = new AnimationManager(new Animation[]{ AnimMove, AnimAttack, AnimDie});
    }

    public void move(){
        this.state = MOVE;

        int left = rectangle.left + speed*sens;
        int top = rectangle.top;
        int right = rectangle.right + speed*sens;
        int bottom = rectangle.bottom;

        this.rectangle.set(left, top, right, bottom);

    }

    public void die(){
        stop = true;
        this.state = DIE;
        source.monsters.remove(this);
        //rendre indétectable pour collisions
    }

    public void attack(){
        stop = true;
        this.state = ATTACK;
    }

    public boolean playerCollide(Player player){
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public boolean projectileCollide(Projectile projectile){
        return Rect.intersects(rectangle, projectile.getRectangle());
    }


    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        if(!stop) move();

        //Gestion des collisions avec les projectiles
        for(int i=0; i<scene.player.getProjectiles().size(); i++){
            Projectile projectile = scene.player.getProjectiles().get(i);
            if(projectileCollide(projectile)){
                die();
                projectile.toDestroy = true;
            }
        }


        animationManager.playAnim(state);
        animationManager.update();


    }

    public Rect getRectangle(){
        return rectangle;
    }
}
