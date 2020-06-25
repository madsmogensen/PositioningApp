package com.example.positioningapp.ServerConnector;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPOutgoing{

    private int port;
    private String serverIP = "192.168.1.22";
    private String msg = "";
    private Thread t;

    public UDPOutgoing(int port){
        this.port = port;
    }

    private void send() {
        try{
            DatagramSocket udpSocket = new DatagramSocket(11000);
            InetAddress serverAddress = InetAddress.getByName(serverIP);
            byte[] buffer = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress,port);
            udpSocket.send(packet);
            //System.out.println("Message sent: " + msg);
            udpSocket.close();
        }catch(Exception e){
            System.out.println("Error sending message " + e.toString());
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        this.msg = msg;
        t = new Thread() {
            @Override
            public void run(){
                send();
            }
        };
        t.start();
    }

    public void stop(){
        if(!t.isInterrupted()){ t.interrupt(); }
    }
}
