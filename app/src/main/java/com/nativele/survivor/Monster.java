package com.nativele.survivor;

/*
* Classe Monster
*
* Représente un monstre.
* Le paramètre type (golem, gobelin ou knight) ainsi que la direction détermine quelles images de monstre doivent être générées.
* */

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

        //Chargement de l'image correspondant au monstre souhaité.
        int id = Constants.CURRENT_CONTEXT.getResources().getIdentifier(type+"_move_" +direction, "drawable", Constants.CURRENT_CONTEXT.getPackageName());
        this.image = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), id);
    }

    /*
    *
    * PARAMÈTRE :  une image et une couleur.
    * RETOUR : l'image tintée avec la couleur souhaitée.
    *
     */
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

    /*
     * Retire un pv au monstre et change sa couleur en fonction de son nombre de pv restant
     */
    public void beHurted(){
            this.pv -= 1;
            this.color = this.pv > 1 ? Color.rgb(255, 127, 0) : Color.RED;
            this.image = changeBitmapColor(this.image, color);
    }

    /*
     * Fait avancer le monstre en direction du joueur
     */
    public void move(){
        int left = rectangle.left + speed*sens;
        int top = rectangle.top;
        int right = rectangle.right + speed*sens;
        int bottom = rectangle.bottom;

        this.rectangle.set(left, top, right, bottom);
    }

    /*
     * Supression du monstre
     */
    public void destroy(){
        stop = true;
        source.getMonsters().remove(this);
    }

    /*
     *
     * PARAMÈTRE :  le player.
     * RETOUR : vrai si le monstre entre en collision avec le joueur
     *
     */
    public boolean playerCollide(Player player){
        return Rect.intersects(rectangle, player.getRectangle());
    }

    /*
     *
     * PARAMÈTRE :  un projectile.
     * RETOUR : vrai si le monstre entre en collision avec le projectile.
     *
     */
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

        if(playerCollide(this.scene.getPlayer())) { // Si le monstre en collision avec le joueur
            if(this.scene.getPlayer().isAlive()) this.scene.getPlayer().die();
            destroy();
        }

        //Gestion des collisions avec les projectiles
        for(int i=0; i<this.scene.getPlayer().getProjectiles().size(); i++){
            if(projectileCollide(this.scene.getPlayer().getProjectiles().get(i))){
                this.scene.getPlayer().getProjectiles().get(i).setToDestroy(true);
                beHurted();
                if(this.pv == 0) { //Si le monstre est mort
                    this.scene.upgradeScore(this.value);  //on augmente le score
                    destroy();
                }
            }
        }

    }

    public Rect getRectangle(){ return rectangle; }
}
