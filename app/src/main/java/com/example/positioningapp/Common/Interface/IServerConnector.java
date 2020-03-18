package com.example.positioningapp.Common.Interface;

import java.io.IOException;

public interface IServerConnector {
    String sendMessage(String msg) throws IOException;
    void stopConnection();
    String getStatus();
}
