package com.example.positioningapp.GUI.DataDrawing;

import android.view.SurfaceHolder;

public class CanvasThread {

    private SurfaceHolder surfaceHolder;
    private DataLayer panel;

    public CanvasThread(SurfaceHolder surfaceHolder, DataLayer panel){
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }
}
