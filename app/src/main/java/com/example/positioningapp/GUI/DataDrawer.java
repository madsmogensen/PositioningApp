package com.example.positioningapp.GUI;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Setup;

import java.util.ArrayList;
import java.util.List;

//Note: View Animation https://developer.android.com/guide/topics/graphics/view-animation.html
public class DataDrawer extends View {

    MotionLayout layout;

    public DataDrawer(Context context, MotionLayout layout) {
        super(context);
        this.layout = layout;
    }

    public void update(Setup setup){
        layout.removeAllViews();
        for(Node node : setup.getNodes().values()){
            ImageView nodeImage = new ImageView(Constants.context);
            nodeImage.setX(50);
            nodeImage.setX(50);
            layout.addView(nodeImage);
        }
        layout.invalidate();
    }
}
