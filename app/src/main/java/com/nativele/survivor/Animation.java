package com.nativele.survivor;

/*
 * Classe Animation
 *
 * Classe qui prend en paramètre un tableau d'image, une durée d'animation et un booléen qui
 * détermine si l'animation doit se répéter à l'inifi à pas.
 *
 * */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;
    private float frameTime;
    private long lastFrame;
    private boolean isPlaying;
    private boolean repeat;
    private boolean haveToStop;

    public Animation(Bitmap[] frames, float animTime, boolean repeat) {
        this.frames = frames;
        this.repeat = repeat;
        this.isPlaying = false;
        this.haveToStop = false;
        frameIndex = 0;

        frameTime = animTime/frames.length; //Temps d'affichage d'une image

        lastFrame = System.currentTimeMillis();
    }

    /*
     * Lance l'animation
     */
    public void play() {
            isPlaying = true;
            frameIndex = 0;
            lastFrame = System.currentTimeMillis();
    }

    /*
     * Stop l'animation
     */
    public void stop() {
        isPlaying = false;
        haveToStop = false;
        frameIndex = 0;
    }


    public void draw(Canvas canvas, Rect destination) {
        if(!isPlaying) return;
        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    public void update() {
        if(!isPlaying)
            return;

        if(System.currentTimeMillis() - lastFrame > frameTime*1000) { //Test si le temps d'affichage d'une image est passé ou pas
            frameIndex++;
            if(frameIndex >= frames.length){ //Si toutes les images ont été affichées
                if(repeat) frameIndex = 0; //Si l'animation doit se répéter à l'infini, on relance l'animation depuis la première image
                else { //Sinon on arrête
                    this.stop();
                    this.haveToStop = true; //Empêche le manager d'animation de relancer l'animation
                }
            }
            lastFrame = System.currentTimeMillis();
        }
    }

    //Accresseurs
    public boolean isPlaying() {
        return isPlaying;
    }
    public boolean haveToStop(){ return haveToStop; }
}