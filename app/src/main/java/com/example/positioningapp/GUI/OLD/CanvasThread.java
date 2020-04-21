package com.example.positioningapp.GUI.OLD;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread{

    //Zoom & pan touch event
    int yOld = 0;
    int yNew = 0;
    int zoomMode = 0;
    float pinchDistanceOld = 0;
    float pinchDistanceNew = 0;
    int zoomControllerScale = 1; //new and old pinch distance to determine zoom scale
    //These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    //Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDistance = 1f;

    //We can be in one of these 3 states
    static final int NONE = 0;
    static final int PAN = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    public static final String TAG = "DebugTag";





    private SurfaceHolder surfaceHolder;
    private Panel panel;
    private boolean run = false;

    public CanvasThread(SurfaceHolder surfaceHolder, Panel panel){
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        Canvas c;
        while (run) {
            c = null;
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    panel.draw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
