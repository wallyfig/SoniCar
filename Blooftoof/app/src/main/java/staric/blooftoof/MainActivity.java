package staric.blooftoof;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private final String DEVICE_ADDRESS = "B8:27:EB:6D:EC:6F";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private static Handler mHandler;

    ImageButton btnConnect,forward_button;
    Switch Connect;
    ImageView wave2, wave3, wave4, wave6, wave7, wave8;

    String command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputStream = new ByteArrayOutputStream(1024);
        setContentView(R.layout.activity_main);

        //Declaration of the button variables
        Connect = (Switch) findViewById(R.id.Connect);
        btnConnect = (ImageButton) findViewById(R.id.btnConnect);
        forward_button = (ImageButton) findViewById(R.id.forward_button);
        wave2 = (ImageView) findViewById(R.id.imageView6);
        wave3 = (ImageView) findViewById(R.id.imageView7);
        wave4 = (ImageView) findViewById(R.id.imageView);
        wave6 = (ImageView) findViewById(R.id.imageView9);
        wave7 = (ImageView) findViewById(R.id.imageView10);
        wave8 = (ImageView) findViewById(R.id.imageView11);

        forward_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "2";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "10";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }


                }

                return false;
            }

        });


        //Tells the connect button to connect once pressed
        Connect.setChecked(false);
        Connect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(BTinit())
                {
                    BTconnect();
                }
            }


        });

    }





    public boolean BTinit()
    {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if(!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter,0);

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }

        return found;
    }


    public boolean BTconnect()
    {
        boolean connected = true;

        try{
            Log.e("", "trying fallback...");

            socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
            socket.connect();

            Log.e("","Connected");

            MainActivity.ConnectedThread mConnectedThread = new MainActivity.ConnectedThread(socket);
            mConnectedThread.start();
        }
        catch (Exception e2){
            Log.e("","Couldn't establish Bluetooth Connection!");

            e2.printStackTrace();
            connected = false;
        }

        if(connected)
        {
            //beef = new MyBluetoothService();
            try
            {
                outputStream = socket.getOutputStream();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return connected;
    }









    @Override
    protected void onStart(){
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
                String irray = errayList.get(0);
                //Irray is sensor 1 on the right
                Log.d("Irray", errayList.get(0));

                String orray = errayList.get(1);
                //Orray is sensor two on the left
                Log.d("orray", errayList.get(1));

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
                if (Objects.equals(orray, "2HIGH")) {
                    wave6.setVisibility(View.VISIBLE);
                    Log.d("", "far");
                } else {
                    wave6.setVisibility(View.INVISIBLE);
                }
                if (Objects.equals(orray, "2MEDIUM")) {
                    wave7.setVisibility(View.VISIBLE);
                    Log.d("", "medium");
                } else {
                    wave7.setVisibility(View.INVISIBLE);
                }

                if (Objects.equals(orray, "2LOW")){
                    wave8.setVisibility(View.VISIBLE);
                    Log.d("", "close");
                }
                else {
                    wave8.setVisibility(View.INVISIBLE);
                }


            }
        };
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        // private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e("", "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("", "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int begin = 0;
            int bytes = 0;
            int numBytes;


            while (true) {
                try {
                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                    for (int i = begin; i < bytes; i++) {
                        if (buffer[i] == "#".getBytes()[0]) {
                            //mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            String oString = new String(buffer);
                            oString = oString.substring(begin, i);
                            Log.d("", String.valueOf(oString.length()));
                            Log.d("", oString);
                            Message dataToSend = Message.obtain();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", oString);
                            dataToSend.setData(bundle);
                            if (MainActivity.mHandler!=null){
                                MainActivity.mHandler.sendMessage(dataToSend);
                            }

                            begin = i + 1;
                            if (i == bytes - 1) {
                                bytes = 0;
                                begin = 0;
                            }
                        }
                    }
                } catch(IOException e){
                    break;
                }
            }
        }


        // Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);


            } catch (IOException e) {
                Log.e("", "Error occurred when sending data", e);

            }
        }

    }

}