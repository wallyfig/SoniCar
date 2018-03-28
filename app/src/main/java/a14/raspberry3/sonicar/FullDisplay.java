package a14.raspberry3.sonicar;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FullDisplay extends AppCompatActivity {


    public static Handler mHandler;
    public OutputStream outputStream;
    String command;

    ImageButton forward_button;
    Button minimize;
    static ImageView wave1,wave5, wave9, wave13, wave16;
    ImageView wave2, wave3, wave4, wave6, wave7, wave8, wave10, wave11, wave12, wave14, wave15, wave17, wave18, wave19, wave20, wave21, wave22, wave23, wave24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_display);

        forward_button = (ImageButton) findViewById(R.id.forward_button);
        minimize = (Button) findViewById(R.id.imageButton2);
        wave1 = (ImageView) findViewById(R.id.imageView);
        wave5 = (ImageView) findViewById(R.id.imageView5);
        wave9 = (ImageView) findViewById(R.id.imageView8);
        wave13 = (ImageView) findViewById(R.id.imageView13);
        wave16 = (ImageView) findViewById(R.id.imageView16);
        wave2 = (ImageView) findViewById(R.id.imageView1);
        wave3 = (ImageView) findViewById(R.id.imageView2);
        wave4 = (ImageView) findViewById(R.id.imageView3);
        wave6 = (ImageView) findViewById(R.id.imageView6);
        wave7 = (ImageView) findViewById(R.id.imageView7);
        wave8 = (ImageView) findViewById(R.id.imageView12);
        wave10 = (ImageView) findViewById(R.id.imageView9);
        wave11 = (ImageView) findViewById(R.id.imageView10);
        wave12 = (ImageView) findViewById(R.id.imageView11);
        wave14 = (ImageView) findViewById(R.id.imageView14);
        wave15 = (ImageView) findViewById(R.id.imageView15);
        wave17 = (ImageView) findViewById(R.id.imageView17);
        wave18 = (ImageView) findViewById(R.id.imageView18);
        wave19 = (ImageView) findViewById(R.id.imageView19);
        wave20 = (ImageView) findViewById(R.id.imageView20);
        wave21 = (ImageView) findViewById(R.id.imageView21);
        wave22 = (ImageView) findViewById(R.id.imageView22);
        wave23 = (ImageView) findViewById(R.id.imageView23);
        wave24 = (ImageView) findViewById(R.id.imageView24);

        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("raspberry3.sonicar.Main");
                startActivity(i);


            }
        });


        forward_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (FragmentSettings.switchButton.isChecked()) {

                    command ="1";
                }
                if(FragmentSettings.switchButton2.isChecked()){

                    command = "2";
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    String send = new String(command);
                    Message dataToSend = Message.obtain();
                    Bundle bundy = new Bundle();
                    bundy.putString("deto", send);
                    dataToSend.setData(bundy);
                    if (FragmentSettings.nHandler!=null){
                        FragmentSettings.nHandler.sendMessage(dataToSend);
                    }



                }


                return false;
            }

        });


    }

    @Override
    public void onStart(){
        super.onStart();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                byte[] writeBuf = (byte[]) msg.obj;
                int begin = (int)msg.arg1;
                int end = (int)msg.arg2;

                String rString = new String();

                mHandler.obtainMessage();
                rString = msg.getData().getString("data");


                String[] erray = rString.split("-");
                List<String> errayList = new ArrayList<>(Arrays.asList(erray));
                Log.d("", String.valueOf(errayList));
                errayList.add("cabbage"); //YOU NEED THIS ADD FUNCTION FOR THE CODE TO WORK DON'T ASK QUESTIONS JUST DO!!!!!!!!!


                if (FragmentSettings.switchButton.isChecked()){
                    String irray = errayList.get(0);
                    //Irray is sensor 1 on the right
                    Log.d("Irray", errayList.get(0));

                    if (Objects.equals(irray, "HIGH")) {
                        wave2.setVisibility(View.VISIBLE);
                        Log.d("", "far");
                    } else {
                        wave2.setVisibility(View.INVISIBLE);
                    }
                    if (Objects.equals(irray, "MEDIUM")) {
                        wave3.setVisibility(View.VISIBLE);
                        Log.d("", "medium");
                    } else {
                        wave3.setVisibility(View.INVISIBLE);
                    }

                    if (Objects.equals(irray, "LOW")){
                        wave4.setVisibility(View.VISIBLE);
                        Log.d("", "close");
                    }
                    else {
                        wave4.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    wave1.setVisibility(View.INVISIBLE);
                    wave2 .setVisibility(View.INVISIBLE);
                    wave3.setVisibility(View.INVISIBLE);
                    wave4.setVisibility(View.INVISIBLE);
                }

                if (FragmentSettings.switchButton2.isChecked()) {
                    String mrray = errayList.get(0);
                    Log.d("mrray", errayList.get(0));

                    String orray = errayList.get(1);
                    //Orray is sensor two on the left
                    Log.d("orray", errayList.get(1));
                    if (Objects.equals(mrray, "HIGH")) {
                        wave6.setVisibility(View.VISIBLE);
                        Log.d("", "far");
                    } else {
                        wave6.setVisibility(View.INVISIBLE);
                    }
                    if (Objects.equals(mrray, "MEDIUM")) {
                        wave7.setVisibility(View.VISIBLE);
                        Log.d("", "medium");
                    } else {
                        wave7.setVisibility(View.INVISIBLE);
                    }

                    if (Objects.equals(mrray, "LOW")){
                        wave8.setVisibility(View.VISIBLE);
                        Log.d("", "close");
                    }
                    else {
                        wave8.setVisibility(View.INVISIBLE);
                    }
                    if (Objects.equals(orray, "2HIGH")) {
                        wave10.setVisibility(View.VISIBLE);
                        Log.d("", "far");
                    } else {
                        wave10.setVisibility(View.INVISIBLE);
                    }
                    if (Objects.equals(orray, "2MEDIUM")) {
                        wave11.setVisibility(View.VISIBLE);
                        Log.d("", "medium");
                    } else {
                        wave11.setVisibility(View.INVISIBLE);
                    }

                    if (Objects.equals(orray, "2LOW")){
                        wave12.setVisibility(View.VISIBLE);
                        Log.d("", "close");
                    }
                    else {
                        wave12.setVisibility(View.INVISIBLE);
                    }

                }
                else{
                    wave5.setVisibility(View.INVISIBLE);
                    wave6 .setVisibility(View.INVISIBLE);
                    wave7.setVisibility(View.INVISIBLE);
                    wave8.setVisibility(View.INVISIBLE);
                    wave9.setVisibility(View.INVISIBLE);
                    wave10 .setVisibility(View.INVISIBLE);
                    wave11.setVisibility(View.INVISIBLE);
                    wave12.setVisibility(View.INVISIBLE);
                }


            }
        };
    }

    }

