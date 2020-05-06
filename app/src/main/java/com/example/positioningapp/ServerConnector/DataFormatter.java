package com.example.positioningapp.ServerConnector;

import com.example.positioningapp.Common.Data.Setup;

import java.util.List;

public class DataFormatter {

    UDPIncoming incoming;
    UDPOutgoing outgoing;
    int port = 11000;

    public DataFormatter(){
        incoming = new UDPIncoming(port);
        outgoing = new UDPOutgoing(port);
    }

    public void sendMessage(String msg){
        outgoing.sendMessage(msg);
    }

    public void stopConnection(){
        incoming.stop();
        outgoing.stop();
    }

    public void update(Setup setup){
        //Update the setup with the new data from incoming
        List<String> temp = incoming.getTempData();
    }
}
