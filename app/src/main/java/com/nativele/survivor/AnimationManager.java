package com.nativele.survivor;

/*
 * Classe AnimationManager
 *
 * Classe qui prend en paramètre un tableau d'Animations et gère l'éxécution des différentes animations d'un objet
 *
 * */

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimationManager {
    private Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    /*
     * PARAMETRE : l'index de l'animation à lancer
     * Lance l'animation ciblée
     */
    public void playAnim(int index) {
        for(int i = 0; i < animations.length; i++) {
            if(i == index) {
                if(!animations[index].isPlaying() && !animations[index].haveToStop())
                    animations[i].play();
            } else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas, Rect rect) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, rect);
    }

    public void update() {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }

    //Accesseurs
    public boolean isAnimationPlaying(int index){
        return animations[index].isPlaying();
    }
}