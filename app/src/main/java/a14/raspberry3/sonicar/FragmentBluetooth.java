package a14.raspberry3.sonicar;

/**
 * Created by eyung on 3/15/2018.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


/**
 * Created by wally on 3/14/2018.
 */

public class FragmentBluetooth extends Fragment {
    View view;


    static Switch Driver1, Driver2;
    Button Login;


    public FragmentBluetooth() {
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bluetooth_fragment,container,false);


        Driver1 = (Switch) view.findViewById(R.id.Driver1);
        Driver2 = (Switch) view.findViewById(R.id.Driver2);
        Login = (Button) view.findViewById(R.id.Login);

//        Login.setOnClickListener(new View.OnClickListener() {
//                                     @Override
//                                     public void onClick(View v) {
//                                         Intent i = new Intent("raspberry3.sonicar.Login");
//                                         startActivity(i);
//
//                                     }
//                                 });
        Driver1.setChecked(false);
        Driver1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                }
            }
            });




        return view;
    }
}
