package a14.raspberry3.sonicar;

/**
 * Created by eyung on 3/15/2018.
 */

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class FragmentDisplay extends Fragment {
    View view;

    public static Handler mHandler;
    public OutputStream outputStream;
    String command;

    ImageButton forward_button;
    //Button fullScreen;
    static ImageView wave1,wave5, wave9;
    ImageView wave2, wave3, wave4, wave6, wave7, wave8, wave10, wave11, wave12;

    public FragmentDisplay() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.display_fragment,container,false);

        forward_button = (ImageButton) view.findViewById(R.id.forward_button);
        wave1 = (ImageView) view.findViewById(R.id.imageView);
        wave5 = (ImageView) view.findViewById(R.id.imageView5);
        wave9 = (ImageView) view.findViewById(R.id.imageView8);
        wave2 = (ImageView) view.findViewById(R.id.imageView1);
        wave3 = (ImageView) view.findViewById(R.id.imageView2);
        wave4 = (ImageView) view.findViewById(R.id.imageView3);
        wave6 = (ImageView) view.findViewById(R.id.imageView6);
        wave7 = (ImageView) view.findViewById(R.id.imageView7);
        wave8 = (ImageView) view.findViewById(R.id.imageView12);
        wave10 = (ImageView) view.findViewById(R.id.imageView9);
        wave11 = (ImageView) view.findViewById(R.id.imageView10);
        wave12 = (ImageView) view.findViewById(R.id.imageView11);
        //fullScreen = (Button) view.findViewById(R.id.imageButton);

//        fullScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent("raspberry3.sonicar.Full_Display");
//                startActivity(i);
//
//
//            }
//        });

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


        return view;
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
