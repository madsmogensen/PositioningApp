package com.example.positioningapp.ServerConnector;

import com.example.positioningapp.Common.Interface.IServerConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerConnector implements IServerConnector {

    private Thread listenerThread = null;
    private Socket socket = null;
    private PrintWriter out;
    private BufferedReader in;
    private String connectionStatus = "Connecting";
    private Boolean wantedConnection = true;

    public ServerConnector() {
        listenerThread = new Thread(startConnection);
        listenerThread.start();
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void stopConnection(){
        try {
            in.close();
        } catch (Exception e) {
            System.out.println("'in' already closed in stopConnection()");
        }
        try {
            out.close();
        } catch (Exception e) {
            System.out.println("'out' already closed in stopConnection()");
        }
        try {
            socket.close();
        } catch (Exception e) {
            System.out.println("'socket' already closed in stopConnection()");
        }
        listenerThread = null;
        connectionStatus = "Not Connected";
        wantedConnection = false;
    }

    @Override
    public String getStatus() {
        return connectionStatus;
    }

    Runnable startConnection = new Runnable() {
        public void run() {
            try {
                socket = new Socket();
                socket.setKeepAlive(true);
                //InetSocketAddress socketAddress = new InetSocketAddress("10.10.10.225", 11000); //Laptop
                InetSocketAddress socketAddress = new InetSocketAddress("192.168.1.22", 11000); //Laptop
                socket.connect(socketAddress,10000); //10 second timeout
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = sendMessage("TEST");
                connectionStatus = "Connected";
                System.out.println("received: " + response);
            } catch (Exception e) {
                System.out.println(e);
                stopConnection();
            }
        }
    };
}

/*
design patterns der understøtter valg af server/client relation

Threading litteratur
google best practice


intro
baggrund - præsenter litteratur: software litteratur



30-40 sider



*/
