package com.example.positioningapp.GUI.DataDrawing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

import com.example.positioningapp.R;

public class BitmapDot{
    private Bitmap bitmap;
    private Paint paint;
    private int x;
    private int y;

    public BitmapDot(Context context, int x, int y){
        Resources res = context.getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.black_dot);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}