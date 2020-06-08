package com.example.positioningapp.GUI;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.Common.Interface.IGUI;

import java.util.List;

public class GuiIntermediary implements IGUI {

    private HUDBottomPanel HUDBottomPanel;
    private HUDMenu HUDMenu;
    private DataDrawerBITMAP dataBitmap;

    public GuiIntermediary(ActionListener listener, List<String> buttonEvents){
        //Instantiate DataDrawer
        dataBitmap = new DataDrawerBITMAP(Constants.context, Constants.mainLayout);
        //Instantiate HUD
        //HUD = new HUD(Constants.context, Constants.mainLayout, buttonEvents);
        HUDBottomPanel = new HUDBottomPanel(Constants.context, Constants.mainLayout, buttonEvents);
        HUDBottomPanel.addActionListener(listener);
        HUDMenu = new HUDMenu(Constants.context, Constants.mainLayout,buttonEvents);
        HUDMenu.addActionListener(listener);
    }

    @Override
    public void update(Setup setup){
        dataBitmap.update(setup);
    }
}
