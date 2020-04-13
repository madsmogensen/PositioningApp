package com.example.positioningapp.GUI;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.Common.Interface.IGUI;

public class GuiIntermediary implements IGUI {

    private DataDrawer dataDrawer;
    private HUD HUD;

    public GuiIntermediary(ActionListener listener){
        //Instantiate HUD
        HUD = new HUD(Constants.context, Constants.HUDLayout);
        HUD.addActionListener(listener);
        //Instantiate DataDrawer
        dataDrawer = new DataDrawer(Constants.context, Constants.DataLayout);
    }

    @Override
    public void update(Setup setup){
        dataDrawer.update(setup);
    }
}
