package a14.raspberry3.sonicar;

/**
 * Created by eyung on 3/15/2018.
 */
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


public class FragmentSettings extends Fragment {
    View view;


    private final String DEVICE_ADDRESS = "B8:27:EB:6D:EC:6F";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    public static Handler nHandler, bHandler;


    TextView textView;
    static TextView textView2;
    Switch btButton;
    static Switch switchButton;
    static Switch switchButton2;
    String switchOn ="Sensor is ON";
    String switchOff ="Sensor is OFF";
    Button Login;

    public FragmentSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.settings_fragment, container, false);

        textView2 = (TextView) view.findViewById(R.id.textView2);
        switchButton = (Switch) view.findViewById(R.id.switchButton);
        switchButton2 = (Switch) view.findViewById(R.id.switchButton2);
        btButton = (Switch)view.findViewById(R.id.btButton);

        btButton.setChecked(false);
        btButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton switchButton, boolean isChecked) {
                if (BTinit()) {
                    BTconnect();
                }
            }


        });

        Login = (Button) view.findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("raspberry3.sonicar.Login");
                startActivity(i);


            }
        });





        switchButton.setChecked(false);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if(bChecked){
                    FragmentDisplay.wave1.setVisibility(View.VISIBLE);
                    switchButton2.setChecked(false);
                } else {
                    FragmentDisplay.wave1.setVisibility(View.INVISIBLE);
                }

            }
        });

//        if(switchButton.isChecked()){
//            textView.setText(switchOn);
//        } else {
//            textView.setText(switchOff);
//        }


//        if(FragmentBluetooth.Driver1.isChecked()){
//            switchButton2.setVisibility(View.VISIBLE);
//            textView2.setVisibility(View.VISIBLE);
//        }

        switchButton2.setChecked(false);
        switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    FragmentDisplay.wave5.setVisibility(View.VISIBLE);
                    FragmentDisplay.wave9.setVisibility(View.VISIBLE);
                    switchButton.setChecked(false);
//                    textView2.setText(switchOn);
                } else {
                    FragmentDisplay.wave5.setVisibility(View.INVISIBLE);
                    FragmentDisplay.wave9.setVisibility(View.INVISIBLE);
//                   textView2.setText(switchOff);
                }

            }
        });




//        if(switchButton2.isChecked()){
//            textView2.setText(switchOn);
//        } else {
//            textView2.setText(switchOff);
//        }

        return view;
    }

    public boolean BTinit() {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            //Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if (bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            //Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }

        return found;
    }


    public boolean BTconnect() {
        boolean connected = true;

        try {
            Log.e("", "trying fallback...");

            socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
            socket.connect();

            Log.e("", "Connected");

            FragmentSettings.ConnectedThread mConnectedThread = new FragmentSettings.ConnectedThread(socket);
            mConnectedThread.start();
        } catch (Exception e2) {
            Log.e("", "Couldn't establish Bluetooth Connection!");

            e2.printStackTrace();
            connected = false;
        }

        if (connected) {
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connected;
    }
    @Override
    public void onStart() {
        super.onStart();
        nHandler = new Handler() {

            public void handleMessage(Message msg) {

                String sent = new String();

                nHandler.obtainMessage();
                sent = msg.getData().getString("deto");
                Log.d("Sent", sent);


                try {
                    outputStream.write(sent.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
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
                            if (FragmentDisplay.mHandler != null) {
                                FragmentDisplay.mHandler.sendMessage(dataToSend);
                            }
                            if(FullDisplay.mHandler != null){
                                FullDisplay.mHandler.sendMessage(dataToSend);
                            }

                            begin = i + 1;
                            if (i == bytes - 1) {
                                bytes = 0;
                                begin = 0;
                            }
                        }
                    }
                } catch (IOException e) {
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
