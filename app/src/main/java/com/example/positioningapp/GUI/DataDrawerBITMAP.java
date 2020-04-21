package com.example.positioningapp.GUI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Note: View Animation https://developer.android.com/guide/topics/graphics/view-animation.html
public class DataDrawerBITMAP extends View implements View.OnDragListener{

    private Paint blackPaint;
    private Paint bluePaint;
    private List<Circle> nodesToDraw = new ArrayList<>();

    private Matrix matrix = new Matrix();

    private CanvasData canvasData;

    Resources res;
    Bitmap blackDot;
    Bitmap blueDot;

    public DataDrawerBITMAP(Context context, ConstraintLayout layout) {
        super(context);

        canvasData = new CanvasData();

        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(6f);
        blackPaint.setStrokeJoin(Paint.Join.ROUND);
        blackPaint.setStrokeWidth(4f);

        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setStrokeJoin(Paint.Join.ROUND);
        bluePaint.setStrokeWidth(4f);

        res = Constants.context.getResources();
        blueDot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.black_dot),30,30,false);
        blackDot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.black_dot),30,30,false);

        layout.addView(this);
    }

    public void update(Setup setup){
        HashMap<String, Node> nodes = setup.getNodes();
        for(Node node : nodes.values()){
            Circle newCircle = new Circle();
            List<Coordinate> coordinates = node.getCoordinates();
            Coordinate currentCoordinate = coordinates.get(coordinates.size()-1);

            newCircle.setX(currentCoordinate.getX());
            newCircle.setY(currentCoordinate.getY());
            newCircle.setBitmap(blackDot);

            nodesToDraw.add(newCircle);
        }
        invalidate();
    }



    //ToDo figure out how to fix so that dragging and update() does not interfere with invalidate(), so that the panning becomes smooth.
    //ToDo then fix the line/path drawing
    //ToDo maybe use PointF class?
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();

        matrix.setTranslate(100, 100);
        matrix.postConcat(canvasData.matrix);
        canvas.drawBitmap(blackDot, matrix, blackPaint);

        boolean first = true;
        for(Circle node : nodesToDraw){
            int actualX = node.getX();
            int actualY = node.getY();
            int drawX = actualX-(node.getBitmap().getWidth()/2);
            int drawY = actualY-(node.getBitmap().getHeight()/2);

            matrix.setTranslate(drawX, drawY);
            matrix.postConcat(canvasData.matrix);
            canvas.drawBitmap(node.getBitmap(), matrix, node.getColor());

            if(first){
                path.moveTo(drawX,drawY);
                first = false;
                continue;
            }
            path.lineTo(drawX,drawY);

        }
        canvas.drawPath(path, blackPaint);
    }

    //@Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    //@Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    //@Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event){
        panZoomWithTouch(event);

        invalidate();//necessary to repaint the canvas
        return true;
    }
*/
    private void panZoomWithTouch(MotionEvent event){
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN://when first finger down, get first point
                canvasData.savedMatrix.set(canvasData.matrix);
                canvasData.start.set(event.getX(), event.getY());
                //Log.d(canvasThread.TAG, "mode=PAN");
                canvasData.mode = canvasData.PAN;
                break;
            case MotionEvent.ACTION_POINTER_DOWN://when 2nd finger down, get second point
                canvasData.oldDistance = spacing(event);
                //Log.d(canvasThread.TAG, "oldDist=" + canvasThread.oldDistance);
                if (canvasData.oldDistance > 10f) {
                    canvasData.savedMatrix.set(canvasData.matrix);
                    midPoint(canvasData.mid, event); //then get the mid point as centre for zoom
                    canvasData.mode = canvasData.ZOOM;
                    //Log.d(canvasThread.TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:       //when both fingers are released, do nothing
                canvasData.mode = canvasData.NONE;
                //Log.d(canvasThread.TAG, "mode=NONE");
                //System.out.println("stopped pan");
                break;
            case MotionEvent.ACTION_MOVE:     //when fingers are dragged, transform matrix for panning
                if (canvasData.mode == canvasData.PAN) {
                    // ...
                    canvasData.matrix.set(canvasData.savedMatrix);
                    canvasData.matrix.postTranslate(event.getX() - canvasData.start.x,
                            event.getY() - canvasData.start.y);
                    //Log.d(canvasThread.TAG,"Mapping rect");
                    //System.out.println("panning");
                    canvasData.start.set(event.getX(), event.getY());
                }
                else if (canvasData.mode == canvasData.ZOOM) { //if pinch_zoom, calculate distance ratio for zoom
                    float newDist = spacing(event);
                    //Log.d(canvasThread.TAG, "newDist=" + newDist);
                    //System.out.println("zooming");
                    if (newDist > 10f) {
                        canvasData.matrix.set(canvasData.savedMatrix);
                        float scale = newDist / canvasData.oldDistance;
                        canvasData.matrix.postScale(scale, scale, canvasData.mid.x, canvasData.mid.y);
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

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        System.out.println("TESTTESTTEST");
        return false;
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        System.out.println("ASFLIASHFLAKJSFHALKJS");
        return super.onDragEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("KEYPRESS");
        return super.onKeyDown(keyCode, event);
    }

    private class CanvasData{
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
    }



    private class Circle{

        private int x;
        private int y;
        private Paint color = bluePaint;
        private Bitmap bitmap;

        public Circle(){ }

        public Bitmap getBitmap() { return bitmap; }

        public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) { this.y = y; }

        public Paint getColor() {
            return color;
        }

        public void setColor(Paint color) {
            this.color = color;
        }
    }
}
