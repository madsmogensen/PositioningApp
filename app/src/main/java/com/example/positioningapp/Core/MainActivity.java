package com.example.positioningapp.Core;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.positioningapp.Common.Interface.IServerConnector;
import com.example.positioningapp.R;
import com.example.positioningapp.ServerConnector.ServerConnector;

import java.io.IOException;
import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity {

    public TextView label;
    public Button button;
    IServerConnector serverConnection = null;
    Boolean running = true;
    Boolean connectToServer = true;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        //Thread to run the while loop
        t = new Thread() {
            @Override
            public void run(){
                try{
                    while(!isInterrupted()){
                        Thread.sleep(10);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                update();
                            }
                        });
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        };

        t.start();
    }

    private void update() {
        label.setText(serverConnection.getStatus());
        //checkServer();
        if (label.getText().equals("false")) {
            t.interrupt();
        }
    }

    public void buttonConnectToServer(View view) {
        System.out.println("Button clicked");
        //connectToServer = !connectToServer;
    }

    /*private void checkServer() {
        if (serverConnection == null && connectToServer) {
            try {
                serverConnection = new ServerConnector();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (serverConnection.connectionStatus && !connectToServer) {
            try {
                serverConnection.stopConnection();
            } catch (Exception e) {
                System.out.println("MainActivity stop connection: " + e);
            }
        }
    }*/

    private void initialize() {
        label = findViewById(R.id.outputLabel);
        button = findViewById(R.id.ServerButton);

        registerIServerConnector();


        try {
            serverConnection = new ServerConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerIServerConnector(){
        try{
            ServiceLoader<IServerConnector> loader = ServiceLoader.load(IServerConnector.class);
            serverConnection = loader.iterator().next().getClass().newInstance();
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
