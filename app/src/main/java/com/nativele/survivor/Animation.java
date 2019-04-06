package com.nativele.survivor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;
    private float frameTime;
    private long lastFrame;
    private boolean isPlaying = false;
    private boolean repeat;
    public boolean haveToStop;

    public Animation(Bitmap[] frames, float animTime, boolean repeat) {
        this.frames = frames;
        this.repeat = repeat;
        this.haveToStop = false;
        frameIndex = 0;

        frameTime = animTime/frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play() {
            isPlaying = true;
            frameIndex = 0;
            lastFrame = System.currentTimeMillis();
    }

    public void stop() {
        isPlaying = false;
        haveToStop = false;
        frameIndex = 0;
    }


    public void draw(Canvas canvas, Rect destination) {
        if(!isPlaying)
            return;

        scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    private void scaleRect(Rect rect) {
        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rect.width() > rect.height())
            rect.left = rect.right - (int)(rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));
    }

    public void update() {
        if(!isPlaying)
            return;

        if(System.currentTimeMillis() - lastFrame > frameTime*1000) {
            frameIndex++;
            if(frameIndex >= frames.length){
                if(repeat) frameIndex = 0;
                else {
                    this.stop();
                    this.haveToStop = true; //Empêche le manager d'animation de relancer l'animation
                }
            }
            //frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}