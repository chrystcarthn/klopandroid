package appdeveloper.klop.klop.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 4/12/2018.
 */

public class SplashScreen extends Activity {
    /** Duration of wait **/
    private static int SPLASH_DISPLAY_LENGTH = 10000;
    private SessionPreference sessions;

    AnimationDrawable animationDrawable;
    FrameLayout frameLayout;

    LinearLayout l1,l2;
    Animation uptodown,downtoup;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splashscreen);

        //Declare Animation and FrameLayout
//        frameLayout = (FrameLayout)findViewById(R.id.myFrameLay);
//        animationDrawable =(AnimationDrawable)frameLayout.getBackground();

//        //Add time changes
//        animationDrawable.setEnterFadeDuration(100);
//        animationDrawable.setExitFadeDuration(200);
//
//        //And start the animation Now
//        animationDrawable.start();

        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        sessions = new SessionPreference(this);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(sessions.isUserLoggedIn() == true)
                {
             //       Toast.makeText(SplashScreen.this, "User Login Status: "+sessions.isUserLoggedIn()+",", Toast.LENGTH_SHORT).show();

                    /* Create an Intent that will start the Menu-Activity. */
                    Intent alreadyLoggedIn = new Intent(SplashScreen.this,TabbedMenu.class);
                    alreadyLoggedIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(alreadyLoggedIn);
                    finish();
                }
                else
                {
                //    Toast.makeText(SplashScreen.this, "User Login Status: "+sessions.isUserLoggedIn()+",", Toast.LENGTH_SHORT).show();

                     /* Create an Intent that will start the Menu-Activity. */
                    Intent loginFirst = new Intent(SplashScreen.this,Login.class);
                    loginFirst.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginFirst);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
