package com.example.positioningapp.Common.Interface;

import com.example.positioningapp.Common.Data.Setup;

public interface INearbyConnector {
    void getUpdate(Setup setup);
    void stop();
}
