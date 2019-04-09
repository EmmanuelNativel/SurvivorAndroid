package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Monster implements Sprite {

    private Rect rectangle;
    private int speed, sens, pv;
    private boolean stop;
    private String direction;
    private GameplayScene scene;
    private MonsterGenerator source;
    Bitmap image;

    public Monster(GameplayScene scene, MonsterGenerator source, Rect rectangle, String type, String direction, int pv){
        this.scene = scene;
        this.source = source;
        this.rectangle = rectangle;
        this.direction = direction;
        this.speed = 5;
        this.sens = this.direction.equals("right") ? 1 : -1;
        this.pv = pv;

        //Animation Marcher :
        int id = Constants.CURRENT_CONTEXT.getResources().getIdentifier(type+"_move_" +direction, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        this.image = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);
    }

    public void move(){
        int left = rectangle.left + speed*sens;
        int top = rectangle.top;
        int right = rectangle.right + speed*sens;
        int bottom = rectangle.bottom;

        this.rectangle.set(left, top, right, bottom);
    }

    public void die(){
        stop = true;
        source.monsters.remove(this);
    }

    public void attack(){
        stop = true;
    }

    public boolean playerCollide(Player player){
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public boolean projectileCollide(Projectile projectile){
        return Rect.intersects(rectangle, projectile.getRectangle());
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, null, rectangle, new Paint());
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
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
