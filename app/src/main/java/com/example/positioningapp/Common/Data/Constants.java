package com.example.positioningapp.Common.Data;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.constraintlayout.motion.widget.MotionLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class Constants {


    //add thread security here
    public static Context context;
    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    public static LinearLayout HUDLayout;
    public static MotionLayout DataLayout;
}
