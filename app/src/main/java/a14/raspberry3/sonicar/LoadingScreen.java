package a14.raspberry3.sonicar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoadingScreen extends AppCompatActivity {
    LinearLayout aa, bb;
    Animation uptodown3, downtoup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);


        aa = (LinearLayout) findViewById(R.id.aa);
        bb = (LinearLayout) findViewById(R.id.bb);
        uptodown3 = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        aa.setAnimation(uptodown3);
        bb.setAnimation(downtoup);

        uptodown3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                startActivity(new Intent(getApplicationContext(),MainActivityEric.class));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
