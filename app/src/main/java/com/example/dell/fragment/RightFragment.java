package com.example.dell.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 * Created by DELL on 2017/3/13.
 */

public class RightFragment extends Fragment {@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.right_fragment, container, false);
    RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.right_layout);
    return view;
}
}
