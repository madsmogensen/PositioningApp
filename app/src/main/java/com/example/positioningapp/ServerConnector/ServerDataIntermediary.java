package com.example.positioningapp.ServerConnector;

import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.IServerConnector;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class ServerDataIntermediary implements IServerConnector {

    private DataFormatter formatter = new DataFormatter();;

    public ServerDataIntermediary() {
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
    public void update(Setup setup, List<String> setupList) {
        formatter.update(setup, setupList);
    }
}
