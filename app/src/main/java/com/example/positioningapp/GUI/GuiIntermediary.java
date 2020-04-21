package com.example.positioningapp.GUI;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.Common.Interface.IGUI;
import com.example.positioningapp.GUI.DataDrawing.DataLayer;
import com.example.positioningapp.GUI.OLD.Panel;

public class GuiIntermediary implements IGUI {

    private DataDrawer dataDrawer;
    private HUD HUD;

    private DataDrawerBITMAP dataBitmap;
    private DataLayer dataLayer;
    private Panel panel;

    public GuiIntermediary(ActionListener listener){
        //Instantiate DataDrawer
        //dataDrawer = new DataDrawer(Constants.context, Constants.mainLayout);
        //Instantiate HUD
        HUD = new HUD(Constants.context, Constants.mainLayout);
        HUD.addActionListener(listener);

        dataBitmap = new DataDrawerBITMAP(Constants.context, Constants.mainLayout);

        //Testing DataLayer instead of dataDrawer;
        //dataLayer = new DataLayer(Constants.mainLayout);
        //panel = new Panel(Constants.context, null);

        //Constants.mainLayout.addView(panel);
    }

    @Override
    public void update(Setup setup){
        //dataDrawer.update(setup);
        //dataLayer.update(setup);
        dataBitmap.update(setup);
    }
}
