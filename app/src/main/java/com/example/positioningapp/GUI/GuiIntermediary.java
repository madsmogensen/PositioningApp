package com.example.positioningapp.GUI;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.Common.Interface.IGUI;
import com.example.positioningapp.GUI.DataDrawing.DataLayer;
import com.example.positioningapp.GUI.OLD.Panel;

import java.util.List;

public class GuiIntermediary implements IGUI {

    private HUD HUD;

    private DataDrawerBITMAP dataBitmap;

    public GuiIntermediary(ActionListener listener, List<String> buttonEvents){
        //Instantiate DataDrawer
        dataBitmap = new DataDrawerBITMAP(Constants.context, Constants.mainLayout);
        //Instantiate HUD
        HUD = new HUD(Constants.context, Constants.mainLayout, buttonEvents);
        HUD.addActionListener(listener);
    }

    @Override
    public void update(Setup setup){
        dataBitmap.update(setup);
    }
}
