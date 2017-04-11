package com.track.experimental.airhockey;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {


    Thread cloneThread;
    public static GameThread gameThread;


    public static Thread gThread;





    @Override
    protected void onCreate(Bundle savedInstanceState) {//Starts the game thread and displays the game view
        super.onCreate(savedInstanceState);
        gThread = Thread.currentThread();
        gameThread =  new GameThread(getApplicationContext());
        cloneThread = gameThread;
        gameThread.start();
        setContentView(gameThread.gameView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setScreenUp();


    }

    void setScreenUp()//Make it full screen but with the navigation still present
    {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
        {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
        }
    }

    @Override
    protected void onResume() {


        gameThread.gameOver=false;
        gameThread.start();//Starts the game thread again
        setScreenUp();
        super.onResume();


    }

    @Override
    protected void onPause() {

        gameThread.gameOver=true;
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
