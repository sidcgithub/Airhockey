package com.track.experimental.airhockey;

/**
 * Created by siddh on 4/3/2017.
 */

public class Paddle {//player paddle

    float x;
    float y;
    float Px =0;
    float Py = 0;
    public float vx = 0;

    public float vy = 0;


    public void setX(float a)
    {
        x=a;
    }

    public void setY(float b)
    {
        y=b;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }


}
