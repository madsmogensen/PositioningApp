package com.example.positioningapp.Common.Interface;

import com.example.positioningapp.Common.Data.Setup;

public interface INearbyConnector {
    void getUpdate(Setup setup, long elapsedTime);
    void stop();
}
