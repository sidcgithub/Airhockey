package com.track.experimental.airhockey;

/**
 * Created by siddh on 4/3/2017.
 */

public class Ball {//Ball object

    float vx = 0,vy=0;
    public float x = 400,y=400;

    float ballRadius = 80;
    public void setX(float a)
    {
        x = a;

    }

    public void setY(float b)
    {
        y=b;
    }

    public void setVx(float a)
    {
        vx = a;
    }
    public void setVy(float b)
    {
        vy = b;
    }

    public float getVx()
    {
        return vx;
    }

    public float getVy()
    {
        return vy;
    }

}
