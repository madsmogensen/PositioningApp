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
import java.util.Map;

//Note: View Animation https://developer.android.com/guide/topics/graphics/view-animation.html
public class DataDrawerBITMAP extends View{

    private Paint blackPaint;
    private Paint bluePaint;
    private List<Circle> nodesToDraw = new ArrayList<>();

    Matrix nodeMatrix = new Matrix();

    private CanvasData canvasData;

    Resources res;
    Bitmap blackDot;
    Bitmap blueDot;

    Bitmap background;

    public DataDrawerBITMAP(Context context, ConstraintLayout layout) {
        super(context);

        canvasData = new CanvasData();

        //move to somewhere else
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(6f*500);
        blackPaint.setStrokeJoin(Paint.Join.ROUND);
        blackPaint.setStrokeWidth(4f);

        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setStrokeJoin(Paint.Join.ROUND);
        bluePaint.setStrokeWidth(4f);

        res = Constants.context.getResources();
        blueDot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.blue_dot),30,30,false);
        blackDot = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.black_dot),30,30,false);

        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.background_gokartcenter), 1708,871,false);

        layout.addView(this);
    }

    public void update(Setup setup){
        HashMap<String, Node> nodes = setup.getNodes();
        nodesToDraw = new ArrayList<>();
        for(Node node : nodes.values()){
            for(int i = 0; i < node.getIndex(); i++){
                Coordinate currentCoordinate = node.getCoordinate(i);
                Circle newCircle = new Circle();
                newCircle.setX(currentCoordinate.getX());
                newCircle.setY(currentCoordinate.getY());
                newCircle.setBitmap(blackDot);

                nodesToDraw.add(newCircle);
            }
        }/*
        if(nodesToDraw.size() > 100){
            nodesToDraw = nodesToDraw.subList(nodesToDraw.size()-101,nodesToDraw.size()-1);
        }*/
        if(nodesToDraw.size() > 0){
            nodesToDraw.get(nodesToDraw.size()-1).setBitmap(blueDot);
        }
        invalidate();
    }


    Circle firstNode;

    //ToDo figure out why pan is not smooth while zoom is?
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Coordinate previousCoordinate = null;

        Matrix backGroundOffset = new Matrix();
        backGroundOffset.postScale(59f,59f);
        backGroundOffset.postTranslate(-37150,-36400);
        backGroundOffset.postConcat(canvasData.matrix);
        canvas.drawBitmap(background,backGroundOffset,blackPaint);


        for(Circle node : nodesToDraw){
            //get coordinate locations
            int actualX = node.getX();
            int actualY = node.getY();
            //invert y
            actualY = canvas.getHeight()-actualY;
            //float stretchFix = 0.5f;
            //actualY = Math.round(actualY * stretchFix);
            int offsetWidth = node.getBitmap().getWidth()/2;
            int offsetHeight = node.getBitmap().getHeight()/2;
            int bitmapCenterX = actualX-offsetWidth;
            int bitmapCenterY = actualY-offsetHeight;

            //center canvas on first node drawn
            if(firstNode == null){
                firstNode = node;
                canvasData.matrix.setTranslate((canvas.getWidth()/2)-bitmapCenterX,(canvas.getHeight()/2)-bitmapCenterY);
            }

            //update matrices
            nodeMatrix.setTranslate(actualX,actualY);
            nodeMatrix.postConcat(canvasData.matrix);

            //get numbers for path
            float[] values = new float[9];
            nodeMatrix.getValues(values);
            float globalX = values[Matrix.MTRANS_X];
            float globalY = values[Matrix.MTRANS_Y];

            //draw line between nodes (skip first)
            if(previousCoordinate != null){
                canvas.drawLine(previousCoordinate.getX(),previousCoordinate.getY(),globalX,globalY,blackPaint);
            }

            canvas.drawBitmap(node.getBitmap(), globalX-offsetWidth, globalY-offsetHeight, node.getColor());

            previousCoordinate = new Coordinate(Math.round(globalX),Math.round(globalY), 0);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        panZoomWithTouch(event);

        invalidate();//necessary to repaint the canvas
        return true;
    }

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

    private class Line{
        private Coordinate start;
        private Coordinate end;

        public Line(Coordinate start, Coordinate end){
            this.start = start;
            this.end = end;
        }

        public Coordinate getStart() {
            return start;
        }

        public void setStart(Coordinate start) {
            this.start = start;
        }

        public Coordinate getEnd() {
            return end;
        }

        public void setEnd(Coordinate end) {
            this.end = end;
        }
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
