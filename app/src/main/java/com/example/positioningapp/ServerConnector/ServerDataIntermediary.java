package com.example.positioningapp.ServerConnector;

import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.IServerConnector;

import java.io.IOException;

public class ServerDataIntermediary implements IServerConnector {

    DataFormatter formatter;

    public ServerDataIntermediary(){
        formatter = new DataFormatter();
    }

    @Override
    public String sendMessage(String msg) {
        formatter.sendMessage(msg);
        return null;
    }

    @Override
    public void stopConnection() {
        formatter.stopConnection();
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public void update(Setup setup) {
        formatter.update(setup);
    }
}
