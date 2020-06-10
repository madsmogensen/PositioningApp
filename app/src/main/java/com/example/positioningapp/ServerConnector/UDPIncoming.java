package com.example.positioningapp.ServerConnector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UDPIncoming {

    private Thread t;
    private List<String> tempData = new ArrayList<>();
    private int port;

    public UDPIncoming(int port){
        this.port = port;
        t = new Thread() {
            @Override
            public void run(){
                try {
                    listen();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void listen() throws IOException {
        //Listen here, put into tempData
        while(true){
            DatagramSocket udpSocket = new DatagramSocket(port); //using the same port to send and receive???
            byte[] msgBuffer = new byte[8000];
            DatagramPacket packet = new DatagramPacket(msgBuffer,msgBuffer.length);
            udpSocket.receive(packet); //the blocking line!
            String text = new String(msgBuffer, 0, packet.getLength());
            tempData.add(text);
        }
    }

    public void stop(){
        t.interrupt();
    }

    public List<String> getTempData(){
        //Return the elements (or a copy) not the reference!
        List<String> returnData = tempData;
        tempData = new ArrayList<>();
        if(returnData.isEmpty()){
            System.out.println("ERROR! RETURNDATA IS EMPTY!");
        }
        return returnData;
    }
}
