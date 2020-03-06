package com.example.positioningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView label;
    Thread serverThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label = findViewById(R.id.outputLabel);
        ServerConnector serverConnection = new ServerConnector();
        serverThread = new Thread(serverConnection);
        serverThread.start();
        label.setText(serverConnection.serverStatus);
    }
}
