package com.example.positioningapp.GUI.OLD;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.positioningapp.R;

public class Panel extends SurfaceView implements SurfaceHolder.Callback{

    CanvasThread canvasThread;

    public Panel (Context context, AttributeSet attrs){
        super(context,attrs);

        getHolder().addCallback(this);
        canvasThread = new CanvasThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        canvasThread.setRunning(true);
        canvasThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        canvasThread.setRunning(false);
        while (retry) {
            try {
                canvasThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        PanZoomWithTouch(event);

        invalidate();//necessary to repaint the canvas
        return true;
    }
    Bitmap blackDot = BitmapFactory.decodeResource(getResources(),
            R.drawable.black_dot);
    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        canvas.drawBitmap(blackDot, canvasThread.matrix, paint);
    }

    /** PanZoomWithTouch function
     /*      Listen to touch actions and perform Zoom or Pan respectively
     /* */
    void PanZoomWithTouch(MotionEvent event){

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN://when first finger down, get first point
                canvasThread.savedMatrix.set(canvasThread.matrix);
                canvasThread.start.set(event.getX(), event.getY());
                Log.d(canvasThread.TAG, "mode=PAN");
                canvasThread.mode = canvasThread.PAN;
                break;
            case MotionEvent.ACTION_POINTER_DOWN://when 2nd finger down, get second point
                canvasThread.oldDistance = spacing(event);
                Log.d(canvasThread.TAG, "oldDist=" + canvasThread.oldDistance);
                if (canvasThread.oldDistance > 10f) {
                    canvasThread.savedMatrix.set(canvasThread.matrix);
                    midPoint(canvasThread.mid, event); //then get the mid point as centre for zoom
                    canvasThread.mode = canvasThread.ZOOM;
                    Log.d(canvasThread.TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:       //when both fingers are released, do nothing
                canvasThread.mode = canvasThread.NONE;
                Log.d(canvasThread.TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_MOVE:     //when fingers are dragged, transform matrix for panning
                if (canvasThread.mode == canvasThread.PAN) {
                    // ...
                    canvasThread.matrix.set(canvasThread.savedMatrix);
                    canvasThread.matrix.postTranslate(event.getX() - canvasThread.start.x,
                            event.getY() - canvasThread.start.y);
                    Log.d(canvasThread.TAG,"Mapping rect");
                    canvasThread.start.set(event.getX(), event.getY());
                }
                else if (canvasThread.mode == canvasThread.ZOOM) { //if pinch_zoom, calculate distance ratio for zoom
                    float newDist = spacing(event);
                    Log.d(canvasThread.TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        canvasThread.matrix.set(canvasThread.savedMatrix);
                        float scale = newDist / canvasThread.oldDistance;
                        canvasThread.matrix.postScale(scale, scale, canvasThread.mid.x, canvasThread.mid.y);
                    }
                }
                break;
        }
    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        // ...
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }



}
