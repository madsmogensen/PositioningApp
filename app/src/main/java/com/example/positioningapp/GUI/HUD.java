package com.example.positioningapp.GUI;

import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.GUI.Decorations.HUDBottomPanel;
import com.example.positioningapp.GUI.Decorations.HUDMenu;

import java.util.ArrayList;
import java.util.List;

public class HUD{

    private List<HUDDecorator> HUDElements = new ArrayList<>();

    public HUD(ActionListener listener, List<String> buttonEvents) {
        HUDElements.add(new HUDMenu(listener, buttonEvents));
        HUDElements.add(new HUDBottomPanel(listener, buttonEvents));
    }
}
