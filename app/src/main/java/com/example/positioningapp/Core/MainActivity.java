package com.example.positioningapp.Core;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.PositioningSetup;
import com.example.positioningapp.Common.Data.TrackedNode;
import com.example.positioningapp.Common.Interface.INearbyConnector;
import com.example.positioningapp.Common.Interface.IServerConnector;
import com.example.positioningapp.NearbyConnector.NearbyConnectorFromFile;
import com.example.positioningapp.R;
import com.example.positioningapp.ServerConnector.ServerConnector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public TextView label;
    public Button button;
    IServerConnector serverConnection;
    INearbyConnector nearbyConnection;
    Thread t;
    PositioningSetup currentSetup;
    LocalDateTime currentTime;
    LocalDateTime lastTime;
    List<Coordinate> drawNodes = new ArrayList<>();

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
        //label.setText(serverConnection.getStatus());
        if (label.getText().equals("false")) {
            t.interrupt();
        }

        /*
        if(currentSetup != null){
            label.setText(currentSetup.getNodes().values().iterator().next().getCoordinates().values().iterator().next().toString());
        }*/
        updateNodes();

        //USE THIS AS TIME INSTEAD!!!!
        System.nanoTime();
    }

    private void updateNodes(){
        for(TrackedNode node : currentSetup.getNodes().values()){
            drawNodes.add(node.getCoordinates().higherEntry(currentTime).getValue());
        }
        //currentTime
    }

    public void buttonConnectToServer(View view) {
        System.out.println("Button clicked");
        //connectToServer = !connectToServer;
    }

    private void initialize() {
        label = findViewById(R.id.outputLabel);
        button = findViewById(R.id.ServerButton);

        serverConnection = new ServerConnector();
        nearbyConnection = new NearbyConnectorFromFile(this);

        UUID id = nearbyConnection.NearbyLookup().keySet().iterator().next();
        currentSetup = nearbyConnection.getSetup(id);
        currentTime = currentSetup.getStartTime();
        lastTime = LocalDateTime.now();
        //serverConnection = registerIServerConnector();
        //nearbyConnection = registerINearbyConnector();
    }

    private IServerConnector registerIServerConnector() {
        ServiceLoader<IServerConnector> loader = null;
        IServerConnector instance = null;
        try{
            loader = ServiceLoader.load(IServerConnector.class);
            System.out.println("loader has next? " + loader.iterator().hasNext());
        }catch(Exception e){
            System.out.println("Error in registerIServerConnector Method 1 " + e);
        }
        try{
            instance = loader.iterator().next().getClass().newInstance();
        }catch(Exception e){
            System.out.println("Error in registerIServerConnector Method 2 " + e);
        }
        return instance;
    }

    private INearbyConnector registerINearbyConnector(){
        ServiceLoader<INearbyConnector> loader = null;
        INearbyConnector instance = null;
        try{
            loader = ServiceLoader.load(INearbyConnector.class);
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            instance = loader.iterator().next().getClass().newInstance();
        }catch(Exception e){
            System.out.println(e);
        }
        return instance;
    }
}
