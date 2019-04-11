package com.nativele.survivor;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameScene extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private SceneManager manager;
    private boolean gameOver;

    public GameScene(Context context) {
        super(context);

        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new GameThread(getHolder(), this);
        manager = new SceneManager();
        gameOver = false;

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
                    intent.putExtra("score",String.valueOf(manager.getScene().getScore()));
                    Constants.GAME_CONTEXT.startActivity(intent);
                    ((Activity)Constants.GAME_CONTEXT).finish();
                }
            }
        };
        th.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        manager.recieveTouch(event);
        return true;
    }

    public void update() {
        if(!gameOver) {
            manager.update();
            if (this.manager.getScene().getPlayer().isGameOver()) {
                gameOver = true;
                surfaceDestroyed(getHolder());
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        manager.draw(canvas);
    }
}