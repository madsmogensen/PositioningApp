package com.example.positioningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public TextView label;
    public Button button;
    ServerConnector serverConnection = null;
    Boolean running = true;
    Boolean connectToServer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        while(running){
            update();
        }
    }

    private void update(){
        label.setText(serverConnection.connectionStatus.toString());
        //checkServer();
        running = false;
    }

    public void buttonConnectToServer(View view){
        System.out.println("Button clicked");
        connectToServer = !connectToServer;
    }

    private void checkServer(){
        if(serverConnection == null && connectToServer){
            try{
                serverConnection = new ServerConnector();
            }catch(Exception e){
                System.out.println(e);
            }
        }else if(serverConnection.connectionStatus && !connectToServer){
            try{
                serverConnection.stopConnection();
            }catch(Exception e){
                System.out.println("MainActivity stop connection: " + e);
            }
        }
    }

    private void initialize(){
        label = findViewById(R.id.outputLabel);
        button = findViewById(R.id.ServerButton);
        try{
            serverConnection = new ServerConnector();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
