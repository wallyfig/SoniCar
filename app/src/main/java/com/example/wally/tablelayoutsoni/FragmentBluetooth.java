package com.example.wally.tablelayoutsoni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wally on 3/14/2018.
 */

public class FragmentBluetooth extends Fragment {
    View view;

    public FragmentBluetooth() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bluetooth_fragment,container,false);
        return view;
    }
}
