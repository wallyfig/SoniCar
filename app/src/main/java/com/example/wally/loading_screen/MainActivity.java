package com.example.wally.loading_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
//import android.graphics.Matrix;
//import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {
    LinearLayout aa,bb;
    Animation uptodown3, downtoup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aa = (LinearLayout) findViewById(R.id.aa);
        bb = (LinearLayout) findViewById(R.id.bb);
        uptodown3 = AnimationUtils.loadAnimation(this,R.anim.uptodown3);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        aa.setAnimation(uptodown3);
        bb.setAnimation(downtoup);
    }
}
