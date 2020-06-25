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
    DatagramSocket udpSocket;

    public UDPIncoming(int port) {
        this.port = port;
        try{
            udpSocket = new DatagramSocket(11005);
        } catch (SocketException e) {
            e.printStackTrace();
        }

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
            byte[] msgBuffer = new byte[8000];

            DatagramPacket packet = new DatagramPacket(msgBuffer,msgBuffer.length);
            //System.out.println("Listening on " + udpSocket. + ":" + udpSocket.getPort());
            udpSocket.receive(packet); //the blocking line!
            String text = new String(msgBuffer, 0, packet.getLength());
            System.out.println("RETRIEVED SOMETHING: " + text);
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
        if(!returnData.isEmpty()){
            System.out.println("Temp data returned");
        }
        return returnData;
    }
}
