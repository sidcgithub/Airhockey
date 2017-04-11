package com.track.experimental.airhockey;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by siddh on 4/3/2017.
 */

public class GameThread extends Thread {//Creates a separate thread for the game calculations

    public static boolean gameOver=false;
    public static GameEngine gameEngine;
    public static GameView gameView;
    Context context;

    public GameThread(Context ctx)

    {
        context = ctx;
        gameView = new GameView(context);
    }



    @Override
    public void run() {


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        float height = size.y;
        gameEngine = new GameEngine(width/2,height/2);

        while(!gameOver)
        {

            gameEngine.update();
        }

        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
