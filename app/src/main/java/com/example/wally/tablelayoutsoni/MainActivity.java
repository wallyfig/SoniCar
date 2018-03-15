package com.example.wally.tablelayoutsoni;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {




    Switch switchButton, switchButton2,btButton;
    TextView textView, textView2, textViewbt;
    String switchOn ="Sensor is ON";
    String switchOff ="Sensor is OFF";
    String connected="BlueTooth Connected";
    String noConnected="Bluetooth is NOT Connected";

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchButton = (Switch) findViewById(R.id.switchButton);
        textView =(TextView) findViewById(R.id.textView);
//
//        switchButton.setChecked(false);
//        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
//                if(bChecked){
//                    textView.setText(switchOn);
//                } else {
//                    textView.setText(switchOff);
//
//                }
//
//            }
//        });
//
//        if(switchButton.isChecked()){
//            textView.setText(switchOn);
//        } else {
//            textView.setText(switchOff);
//        }

        switchButton2 = (Switch) findViewById(R.id.switchButton2);
        textView2 =(TextView) findViewById(R.id.textView2);

//        switchButton2.setChecked(false);
//        switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
//                if(bChecked){
//                    textView2.setText(switchOn);
//                } else {
//                    textView2.setText(switchOff);
//
//                }
//
//            }
//        });
//
//        if(switchButton2.isChecked()){
//            textView2.setText(switchOn);
//        } else {
//            textView2.setText(switchOff);
//        }

        btButton = (Switch) findViewById(R.id.btButton);
        textViewbt =(TextView) findViewById(R.id.textViewbt);

//        btButton.setChecked(false);
//        btButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
//                if(bChecked){
//                    textViewbt.setText(switchOn);
//                } else {
//                    textViewbt.setText(switchOff);
//
//                }
//
//            }
//        });
//
//        if(btButton.isChecked()){
//            textViewbt.setText(switchOn);
//        } else {
//            textViewbt.setText(switchOff);
//        }
//
//



        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager= (ViewPager) findViewById(R.id.viewpagerid) ;
//        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentSettings(),"Settings");
        adapter.AddFragment(new FragmentDisplay(),"Display");
        adapter.AddFragment(new FragmentBluetooth(),"BlueTooth");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
