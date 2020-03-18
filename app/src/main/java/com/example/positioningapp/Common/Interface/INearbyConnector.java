package com.example.positioningapp.Common.Interface;

import com.example.positioningapp.Common.Data.PositioningSetup;

import java.util.HashMap;

public interface INearbyConnector {
    void NearbyLookup();
    void connectToSetup(int ID);
    void connectToSetup(String name);

}
