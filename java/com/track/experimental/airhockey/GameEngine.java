package com.track.experimental.airhockey;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by siddh on 4/3/2017.
 */

public class GameEngine {//Does the calculations for the game. Also creates the different frames.

    Boolean refresh = false;

    Paddle paddle1 = new Paddle();
    Paddle paddle2 = new Paddle();
    Goal goal1 = new Goal();
    Goal goal2 = new Goal();
    Ball ball = new Ball();
    float screenWidth, screenHeight;


    public GameEngine(float ballx, float bally)
    {

        screenWidth = ballx*2;
        screenHeight = bally*2;
        ball.setX(ballx);
        ball.setY(bally);

    }


    public void setPlayer2(float x, float y) {


        paddle2.setX(x);

        if(y<screenHeight/2)
        paddle2.setY(y);
        else
        {
            paddle2.setY(screenHeight/2);
        }

        GameThread.gameView.drawPlayer2(paddle2);
    }

    public void setPlayer1(float x, float y) {

        paddle1.setX(x);

        if(y>screenHeight/2)
            paddle1.setY(y);
        else
        {
            paddle1.setY(screenHeight/2);
        }



        GameThread.gameView.drawPlayer1(paddle1);
    }

    public Paddle paddleVelocity(Paddle paddle)
    {

        float velX, velY;
        float Px = paddle.Px;
        float Py = paddle.Py;

        velX = -(Px - paddle.getX());
        velY = -(Py - paddle.getY());
        paddle.Px = paddle.getX();
        paddle.Py = paddle.getY();
        paddle.vx = velX;
        paddle.vy = velY;
        return paddle;

    }

    public void update() {
       paddle1 = paddleVelocity(paddle1);
        paddle2 = paddleVelocity(paddle2);
        ballCollisionsWalls();
        ballCollisionPaddle(paddle1);
        ballCollisionPaddle(paddle2);
        ballUpdate();






    }

    void drawOnUi(final Ball ball)
    {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {

                GameThread.gameView.drawBall(ball);

            }
        });

    }





    public void ballUpdate()
    {
        float vx = ball.getVx();
        float vy = ball.getVy();
        float vrxy;

        drawOnUi(ball);
        vrxy = Math.abs(vx) / (Math.abs(vx) + Math.abs(vy));
        float vxy =(Math.abs(vx) + Math.abs(vy));
        float vyr = 1 - vrxy;


        if(Math.abs(vx)+Math.abs(vy)!=0&&vrxy>0.1&&vyr>0.1) {
                int ctr =0;
                for (float i = 0; i <= vxy; i = i + 10) {
                    try {
                        Thread.sleep((int)Math.ceil(80/vxy));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(vx!=0)
                    {
                        ball.x = (ball.x + 10*vrxy*vx/Math.abs(vx));
                    }

                    if(vy!=0)
                   {
                        ball.y = (ball.y + 10*vyr*vy/Math.abs(vy));
                    }
                    drawOnUi(ball);
            }
        }


        else
        {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           ball.x +=ball.getVx();
            ball.y +=ball.getVy();
            drawOnUi(ball);
        }
    }



    public void ballCollisionsWalls()
    {
        if(ball.getVx()<0&&ball.x<=ball.ballRadius)
        {
            ball.setVx(-ball.getVx());
        }
        if(ball.getVx()>0&&ball.x>=screenWidth-ball.ballRadius)
        {
            ball.setVx(-ball.getVx());
        }

        if(ball.getVy()<0&&ball.y<=ball.ballRadius)
        {
            ball.setVy(-ball.getVy());

            if(ball.x>screenWidth/2-100&&ball.x<screenWidth/2+100)
            {
               GameView.player2Score +=1;
                resetBall();
            }
        }

        if(ball.getVy()>0&&ball.y>=screenHeight-ball.ballRadius)
        {
            ball.setVy(-ball.getVy());
            if(ball.x>screenWidth/2-100&&ball.x<screenWidth/2+100)
            {
                GameView.player1Score +=1;
                resetBall();
            }
        }

    }

    public void resetBall()
    {
        ball.setVx(0);
        ball.setVy(0);
        ball.x = screenWidth/2;
        ball.y = screenHeight/2;
    }


    public void ballCollisionPaddle(Paddle paddle)
    {
        double dist =  (Math.pow(ball.x-paddle.getX(),2) + Math.pow(ball.y-paddle.getY(),2));
        if(dist<Math.pow(2*ball.ballRadius,2))
        {
            ball.setVx(ball.getVx()+paddle.vx/2);
            ball.setVy(ball.getVy()+paddle.vy/2);

        }
    }


}
