package com.nativele.survivor;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private boolean gameOver;

    private GameplayScene gameScene;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new GameThread(getHolder(), this);
        gameOver = false;

        gameScene = new GameplayScene();

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Thread th = new Thread(){
            @Override
            public void run() {
                try{
                    thread.setRunning(false);
                    thread.join();
                } catch (Exception e){ e.printStackTrace(); }
                finally {
                    Intent intent = new Intent(Constants.GAME_CONTEXT, GameOver.class);
                    intent.putExtra("score",String.valueOf(gameScene.getScore()));
                    Constants.GAME_CONTEXT.startActivity(intent);
                    ((Activity)Constants.GAME_CONTEXT).finish();
                }
            }
        };
        th.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameScene.recieveTouch(event);
        return true;
    }

    public void update() {
        if(!gameOver) {
            gameScene.update();
            if (gameScene.getPlayer().isGameOver()) {
                gameOver = true;
                surfaceDestroyed(getHolder());
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        gameScene.draw(canvas);
    }
}