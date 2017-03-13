package com.example.dell.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
这里的Fragment需要导入的包应该是android.support.v4.app.Fragment的包
因为它可以让碎片在所有的Android系统版本中保持一致性
而系统提供的Android.app.Fragment不能够支持，当需要运行的环境在4.2系统之前的时候
运行的程序就会崩溃
* */

/**
 * Created by DELL on 2017/3/13.
 */

public class LeftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.left_fragment, container, false);
        return view;
    }
}
