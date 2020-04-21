package com.example.positioningapp.GUI.DataDrawing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//ToDo check if this works as it is: Should just draw (giant, please rescale) black dots at the locations
public class DataLayer extends SurfaceView implements SurfaceHolder.Callback{

    CanvasThread canvasThread;
    List<Bitmap> nodesToDraw = new ArrayList<>();
    Matrix matrix = new Matrix();
    Resources res;
    Bitmap blackDot;
    Bitmap blueDot;

    public DataLayer(ConstraintLayout parent) {
        super(Constants.context);
        getHolder().addCallback(this);

        canvasThread = new CanvasThread(getHolder(),this);
        setFocusable(true);
        parent.addView(this);
        res = Constants.context.getResources();
        blackDot = BitmapFactory.decodeResource(res, R.drawable.black_dot);
        blueDot = BitmapFactory.decodeResource(res, R.drawable.black_dot);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Bitmap bitmap : nodesToDraw){
            Paint paint = new Paint();
            paint.setColor(Color.TRANSPARENT);
            canvas.drawBitmap(bitmap, 0,0, paint);
        }
    }

    public void update(Setup setup){
        nodesToDraw = new ArrayList<>();
        HashMap<String, Node> nodes = setup.getNodes();
        for(Node node : nodes.values()){
            for(Coordinate coordinate : node.getCoordinates()){
                nodesToDraw.add(blackDot);
            }
        }
        invalidate();
    }
}
