//Activity untuk Splash Screen
package com.test.mynewsreader;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class SplashScreenActivity extends Activity {
 	private long ms=0;
    //waktu tayang splashscreen 3000 millisecond = 3 detik 
    private long splashTime = 3000;
    private boolean splashActive = true;
    private boolean paused = false;
 

@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.splashscreen);
	getActionBar().hide();
	Thread mythread = new Thread(){
        public void run(){
            try {
                while (splashActive && ms < splashTime){
                    if(!paused)
                        ms=ms+100;
                    sleep(100);
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                finish();
                startActivity(i);
                
            }
        }
    };
    mythread.start();
}


}
