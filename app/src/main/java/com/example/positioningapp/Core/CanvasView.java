package com.example.positioningapp.Core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.positioningapp.Common.Data.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private List<Coordinate> drawNodes;
    private int canvasSize;


    public CanvasView(Context context, AttributeSet attrs){
        super(context, attrs);

        mBitmap = Bitmap.createBitmap(2000,2000,Bitmap.Config.ARGB_8888);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        // Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,0,0,null);
        canvasSize = canvas.getWidth();
        //canvas.drawCircle(50,50,5,mPaint); //hardcoded drawn dot

        if(drawNodes == null){return;}
        for(Coordinate coord : drawNodes){
            System.out.println("drawing circle at " + coord.getX() + "," + coord.getY());
            canvas.drawCircle(coord.getX(),coord.getY(),5,mPaint);
        }
    }

    public void clearCanvas() {
        //mPath.reset();
        invalidate();
    }

    public void updateNodes(List<Coordinate> drawNodes, int minValue, int maxValue){
        List<Coordinate> tempDrawNodes = new ArrayList<>();
        for(Coordinate cord : drawNodes){
            double difference = maxValue-minValue;
            double canvasDifference = canvasSize-0;
            double newX = (cord.getX()-minValue)/difference*canvasDifference;
            double newY = (cord.getY()-minValue)/difference*canvasDifference;
            Coordinate tempCoord = new Coordinate((int)Math.round(newX), (int)Math.round(newY),0);
            tempDrawNodes.add(tempCoord);
        }
        this.drawNodes = tempDrawNodes;
        invalidate();
    }
    //new_value = (old_value - old_bottom) / (old_top - old_bottom) * (new_top - new_bottom) + new_bottom
    //new_value = (old_value - old_bottom) / (old_top - old_bottom) * (canvasSize - 0) + 0
    //Y = (X-A)/(B-A) * (D-C) + C
}
