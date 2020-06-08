package com.example.positioningapp.ServerConnector;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPOutgoing{

    int port;
    String serverIP = "192.168.1.22";
    String msg = "";
    Thread t;

    public UDPOutgoing(int port){
        this.port = port;
        t = new Thread() {
            @Override
            public void run(){
                send();
            }
        };
    }

    public void send() {
        try{
            DatagramSocket udpSocket = new DatagramSocket(port);
            InetAddress serverAddress = InetAddress.getByName(serverIP);
            byte[] buffer = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress,port);
            udpSocket.send(packet);
            System.out.println("Message sent: " + msg);
        }catch(Exception e){
            System.out.println("Error sending message " + e.toString());
        }
    }

    public void sendMessage(String msg){
        this.msg = msg;
        t.start();
    }

    public void stop(){
        if(!t.isInterrupted()){ t.interrupt(); }
    }
}
