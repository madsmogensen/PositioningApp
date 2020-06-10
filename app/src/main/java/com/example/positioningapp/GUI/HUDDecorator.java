package com.example.positioningapp.GUI;

import android.content.Context;
import android.view.View;
import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Interface.ActionListener;
import java.util.List;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class HUDDecorator extends View {

    private Context context;
    private ConstraintLayout mainLayout;

    public HUDDecorator(List<String> buttonEvents, ActionListener listener) {
        super(Constants.context);
        this.context = Constants.context;
        this.mainLayout = Constants.mainLayout;
    }

    public ConstraintLayout getMainLayout(){
        return mainLayout;
    }
}
