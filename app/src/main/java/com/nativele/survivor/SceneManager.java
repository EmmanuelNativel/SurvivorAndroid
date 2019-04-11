package com.nativele.survivor;

import android.graphics.Canvas;
import android.view.MotionEvent;
import java.util.ArrayList;

public class SceneManager {

    public static int ACTIVE_SCENE;

    private ArrayList<Scene> scenes = new ArrayList<>();

    public SceneManager(){
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public GameplayScene getScene(){ return (GameplayScene)scenes.get(ACTIVE_SCENE); }
}
