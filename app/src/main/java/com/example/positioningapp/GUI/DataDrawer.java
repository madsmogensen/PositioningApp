package com.example.positioningapp.GUI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
public class DataDrawer extends View {

    private Paint blackPaint;
    private Paint bluePaint;
    private List<Circle> nodesToDraw;
    private List<Circle> previousNodes = new ArrayList<>();
    private int highestXValue = Integer.MIN_VALUE;
    private int lowestXValue = Integer.MAX_VALUE;
    private int canvasWidth;
    private int highestYValue = Integer.MIN_VALUE;
    private int lowestYValue = Integer.MAX_VALUE;
    private int canvasHeight;

    public DataDrawer(Context context, ConstraintLayout layout) {
        super(context);

        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);
        blackPaint.setStrokeJoin(Paint.Join.ROUND);
        blackPaint.setStrokeWidth(4f);

        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setStrokeJoin(Paint.Join.ROUND);
        bluePaint.setStrokeWidth(4f);

        layout.addView(this);
    }

    public void update(Setup setup){
        nodesToDraw = new ArrayList<>();
        HashMap<String, Node> nodes = setup.getNodes();
        for(Node node : nodes.values()){
            Circle newCircle = new Circle();
            List<Coordinate> coordinates = node.getCoordinates();
            Coordinate currentCoordinate = coordinates.get(coordinates.size()-1);

            lowestXValue = Math.min(lowestXValue, currentCoordinate.getX());
            highestXValue = Math.max(highestXValue, currentCoordinate.getX());
            lowestYValue = Math.min(lowestYValue, currentCoordinate.getY());
            highestYValue = Math.max(highestYValue, currentCoordinate.getY());

            newCircle.setX(currentCoordinate.getX());
            newCircle.setY(currentCoordinate.getY());

            nodesToDraw.add(newCircle);
            previousNodes.add(newCircle);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        Resources res = Constants.context.getResources();
        Bitmap blackDot = BitmapFactory.decodeResource(res, R.drawable.black_dot);
        Bitmap blueDot = BitmapFactory.decodeResource(res, R.drawable.black_dot);
        canvas.drawBitmap(blueDot, 10,50, bluePaint);

        int previousX = -1; //negative 1 so it doesn't try to draw from the first node
        int previousY = -1;

        for(int i = 0; i < previousNodes.size()-1; i++){ //don't draw last note as it is the newest one being drawn below

            Circle canvasCoordinate = normalizeCoordinates(previousNodes.get(i));
            int x = canvasCoordinate.getX();
            int y = canvasCoordinate.getY();
            canvasCoordinate.setColor(blackPaint);
            canvas.drawCircle(x, y,canvasCoordinate.getRadius(), canvasCoordinate.getColor());
            if(previousX >= 0 && previousY >= 0){
                canvas.drawLine(previousX,previousY,x,y,blackPaint);
            }
            previousX = x;
            previousY = y;
        }
        for(Circle node : nodesToDraw){
            Circle canvasCoordinate = normalizeCoordinates(node);
            int x = canvasCoordinate.getX();
            int y = canvasCoordinate.getY();
            canvas.drawCircle(x, y,canvasCoordinate.getRadius(), canvasCoordinate.getColor());
            System.out.println("drawing node at " + x + "," + y);
            if(previousX >= 0 && previousY >= 0){
                canvas.drawLine(previousX,previousY,x,y,blackPaint);
            }
        }
    }

    private Circle normalizeCoordinates(Circle coordinate){
        /*
        * If your number X falls between A and B, and you would like Y to fall between C and D, you can apply the following linear transform:
        * Y = (X-A)/(B-A) * ((D-C) + C)
        * X = actual coordinate
        * A = setup min, B = setup max
        * C = canvas min, D = canvas max
        */

        int resultX = Math.round(((float)coordinate.getX() - (float)lowestXValue)/((float)highestXValue-(float)lowestXValue) * (((float)canvasWidth-50f)+50f));
        int resultY = Math.round(((float)coordinate.getY() - (float)lowestYValue)/((float)highestYValue-(float)lowestYValue) * (((float)canvasHeight-50f)+50f));
        resultY = canvasHeight-resultY; //Invert Y to match excel coordinates for comparison

        Circle result = new Circle();
        result.setX(resultX);
        result.setY(resultY);
        result.setRadius(coordinate.getRadius());
        result.setColor(coordinate.getColor());

        return result;
    }

    private class Circle{

        private int x;
        private int y;
        private int radius = 10;
        private Paint color = bluePaint;

        public Circle(){

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

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public Paint getColor() {
            return color;
        }

        public void setColor(Paint color) {
            this.color = color;
        }
    }
}
