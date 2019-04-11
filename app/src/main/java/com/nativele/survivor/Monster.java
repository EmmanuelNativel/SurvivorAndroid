package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

public class Monster implements Sprite {

    private Rect rectangle;
    private int speed, sens, pv, color, value;
    private boolean stop;
    private String direction;
    private GameplayScene scene;
    private MonsterGenerator source;
    private Bitmap image;

    public Monster(GameplayScene scene, MonsterGenerator source, Rect rectangle, String type, String direction, int pv){
        this.scene = scene;
        this.source = source;
        this.rectangle = rectangle;
        this.direction = direction;
        this.speed = 3;
        this.sens = this.direction.equals("right") ? 1 : -1;
        this.pv = pv;
        this.value = this.pv;
        this.color = 0;

        int id = Constants.CURRENT_CONTEXT.getResources().getIdentifier(type+"_move_" +direction, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        this.image = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);
    }

    public static Bitmap changeBitmapColor(Bitmap sourceBitmap, int color)
    {
        Bitmap resultBitmap = sourceBitmap.copy(sourceBitmap.getConfig(),true);
        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        paint.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);
        return resultBitmap;
    }

    public void beHurted(){
            this.pv -= 1;
            this.color = this.pv > 1 ? Color.rgb(255, 127, 0) : Color.RED;
            this.image = changeBitmapColor(this.image, color);
    }

    public void move(){
        int left = rectangle.left + speed*sens;
        int top = rectangle.top;
        int right = rectangle.right + speed*sens;
        int bottom = rectangle.bottom;

        this.rectangle.set(left, top, right, bottom);
    }

    public void destroy(){
        stop = true;
        source.getMonsters().remove(this);
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

        if(playerCollide(this.scene.getPlayer())) {
            if(this.scene.getPlayer().isAlive()) this.scene.getPlayer().die();
            destroy();
        }

        //Gestion des collisions avec les projectiles
        for(int i=0; i<this.scene.getPlayer().getProjectiles().size(); i++){
            if(projectileCollide(this.scene.getPlayer().getProjectiles().get(i))){
                this.scene.getPlayer().getProjectiles().get(i).setToDestroy(true);
                beHurted();
                if(this.pv == 0) {
                    this.scene.upgradeScore(this.value);
                    destroy();
                }
            }
        }

    }

    public Rect getRectangle(){ return rectangle; }
}
