package com.nativele.survivor;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    void update();
    void draw(Canvas canvas);
    void recieveTouch(MotionEvent event);

}
