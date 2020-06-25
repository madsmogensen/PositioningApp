package com.example.positioningapp.Common.Interface;

import com.example.positioningapp.Common.Data.Setup;

import java.io.IOException;
import java.util.List;

public interface IServerConnector {
    String sendMessage(String msg);
    void stopConnection();
    String getStatus();
    void update(Setup setup, List<String> setupList);
}
