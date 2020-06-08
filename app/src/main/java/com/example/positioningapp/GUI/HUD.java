package com.example.positioningapp.GUI;

import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.GUI.Decorations.HUDBottomPanel;
import com.example.positioningapp.GUI.Decorations.HUDMenu;

import java.util.ArrayList;
import java.util.List;

public class HUD{

    private List<HUDDecorator> HUDElements = new ArrayList<>();

    public HUD(List<String> buttonEvents, ActionListener listener) {
        HUDElements.add(new HUDMenu(buttonEvents, listener));
        HUDElements.add(new HUDBottomPanel(buttonEvents, listener));
    }
}
