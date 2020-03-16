package com.example.positioningapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnector {

    Thread listenerThread = null;
    Socket socket = null;
    PrintWriter out;
    BufferedReader in;
    Boolean connectionStatus = true;
    Boolean wantedConnection = true;

    public ServerConnector() throws IOException {
        System.out.println("Connector startet????");
        listenerThread = new Thread(startConnection);
        listenerThread.start();
        System.out.println("After the thread start");
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String response = in.readLine();
        return response;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
        listenerThread = null;
        connectionStatus = false;
        wantedConnection = false;
    }

    Runnable startConnection = new Runnable(){
        public void run(){
            try{
                socket = new Socket("10.10.10.225", 11000);
                socket.setKeepAlive(true);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = sendMessage("TESTTESTTEST");
                System.out.println("reveived: " + response);
            }
            catch(Exception e){
                System.out.println("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ");
                System.out.println(e);
                System.out.println("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ");
            }
        }
    };





    /*public void run() {
        Socket socket;
        try {
            socket = new Socket("localhost", 11000);
            serverStatus = "Connected";
            //output = new PrintWriter(socket.getOutputStream());
            //input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //tvMessages.setText("Connected\n");
                }
            });
            //new Thread(new Thread2()).start();
        } catch (IOException e) {
            serverStatus = "Can't connect to the server";
            e.printStackTrace();
        }
        System.out.println("######################################################################################################");
        System.out.println(serverStatus);
        System.out.println("######################################################################################################");
    }*/

}
