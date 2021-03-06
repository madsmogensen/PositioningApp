package com.example.positioningapp.Common.Data;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class Constants {


    //add thread security here
    public static Context context;
    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    public static final DateFormat dfs = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
    public static ConstraintLayout mainLayout;
}
