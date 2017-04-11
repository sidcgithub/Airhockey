package com.track.experimental.airhockey;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import static com.track.experimental.airhockey.GameThread.gameEngine;
import static java.lang.String.*;

/**
 * Created by siddh on 4/3/2017.
 */

public class GameView extends View{//Takes care of all visuals


    Thread player1Thread;
    Thread player2Thread;
    Thread ballThread;
    Thread goalThread;

    public static int player2Score=0,player1Score=0;


    public static final int MAX_FINGERS = 2;//Only 2 fingers allowed in the game
    Paint PaddleColor1 = new Paint();
    Paint PaddleColor2 = new Paint();
    Paint BallColor = new Paint();

    Paddle paddle1 ;
    Paddle paddle2;

    Context ctx;

    public GameView(Context context) {
        super(context);
        ctx = context;
        setWillNotDraw(false);
        setBackgroundColor(Color.BLACK);


    }



    public void drawPlayer1(Paddle paddle) {//updates player 1 position
        this.invalidate();
        PaddleColor1.setColor(Color.GREEN);
        paddle1 = paddle;
    }


    public void drawPlayer2(Paddle paddle) {//Updates the player2 position

        PaddleColor2.setColor(Color.BLUE);
        paddle2 = paddle;
    }
    Ball ball;

    public void drawBall(Ball b) {//Updates the ball position and invalidates the view
        ball =b;
        invalidate();

    }

    Paint goalColor = new Paint();

    int r = 0;
    int g = 128;
    int b = 254;
    int rflag = 1;
    int gflag = 1;
    int bflag =1;

    @Override
    protected void onDraw(Canvas canvas) { //Draws the ball, paddle, goals and all boundaries

        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        float height = size.y;

        Paint whiteLine = new Paint();
        whiteLine.setColor(Color.WHITE);
        whiteLine.setStrokeWidth(10);
        whiteLine.setStyle(Paint.Style.STROKE);


        canvas.drawLine(0,height/2,width,height/2,whiteLine);
        canvas.drawCircle(width/2,height/2,width/4,whiteLine);

        canvas.drawCircle(width/2,height,200,whiteLine);
        canvas.drawCircle(width/2,0,200,whiteLine);

        if(r>254)
            rflag =-1;
        if(g>254)
            gflag=-1;
        if(b>254)
        {
            bflag=-1;
        }

        if(r<0)
            rflag =1;
        if(g<0)
            gflag=1;
        if(b<0)
        {
            bflag=1;
        }

        r+=rflag;
        g+=gflag;
        b+=bflag;

        int r2 = 255 - r;
        int g2 = 255 - g;
        int b2 = 255-b;

        goalColor.setColor(Color.rgb(r,g,b));
        canvas.drawCircle(width/2,0,100,goalColor);
        goalColor.setColor(Color.rgb(r2,g2,b2));
        canvas.drawCircle(width/2,height,100,goalColor);
        Paint score = new Paint();
        score.setColor(Color.WHITE);
        score.setTextSize(30);
        canvas.drawText(Integer.toString(player2Score),(int)(width/2),40,score);
        canvas.drawText(Integer.toString(player1Score),(int)(width/2),height-40,score);

        if(paddle1!=null)
            canvas.drawCircle(paddle1.getX(),paddle1.getY(),80,PaddleColor1);
        if(paddle2!=null)
            canvas.drawCircle(paddle2.getX(),paddle2.getY(),80,PaddleColor2);

        BallColor.setColor(Color.RED);



        if(ball!=null)
        {
            canvas.drawCircle(ball.x,ball.y,ball.ballRadius,BallColor);
        }
    }


    boolean touch= false;

    int mFingerPaths[] = new int[MAX_FINGERS];

    @Override
    public boolean onTouchEvent(MotionEvent event) {//Dtects movements of 2 different fingers

        int pointerCount = event.getPointerCount();
        int cappedPointerCount = pointerCount > MAX_FINGERS ? MAX_FINGERS : pointerCount;
        int actionIndex = event.getActionIndex();
        int action = event.getActionMasked();
        int id = event.getPointerId(actionIndex);



        if ((action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) && id < MAX_FINGERS) {
            mFingerPaths[id]=1;

            if(action==MotionEvent.ACTION_POINTER_DOWN)
            {
                gameEngine.setPlayer2(event.getX(actionIndex), event.getY(actionIndex));
            }

            else if(action==MotionEvent.ACTION_DOWN)
            {
                gameEngine.setPlayer1(event.getX(actionIndex), event.getY(actionIndex));

            }
        } else if ((action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_UP) && id < MAX_FINGERS) {

            mFingerPaths[id] = 0;
        }

        for(int i = 0; i < cappedPointerCount; i++) {
            if(mFingerPaths[i] != 0) {
                int index = event.findPointerIndex(i);
                if(index == 0)
                {
                    gameEngine.setPlayer1(event.getX(index), event.getY(index));
                }
                else
                {
                    gameEngine.setPlayer2(event.getX(index), event.getY(index));
                }



            }
        }

        touch=true;



        return true;
    }


    public void viewInvalidate() {


//
//        while(touch==false)
//        {
//            try {
//                Thread.sleep(50);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            gameEngine.update();
//
//        }


    }
}
