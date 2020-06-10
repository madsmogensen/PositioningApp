package com.example.positioningapp.GUI;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.Common.Interface.IGUI;

import java.util.List;

public class GuiIntermediary implements IGUI {

    private HUD HUD;
    private DataDrawer dataBitmap;

    public GuiIntermediary(ActionListener listener, List<String> buttonEvents){
        //Instantiate DataDrawer
        dataBitmap = new DataDrawer(Constants.context, Constants.mainLayout);
        //Instantiate HUD
        HUD = new HUD(listener, buttonEvents);
    }

    @Override
    public void update(Setup setup){
        dataBitmap.update(setup);
    }
}
