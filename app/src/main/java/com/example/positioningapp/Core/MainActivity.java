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

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.TreeMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public TextView label;
    public Button button;
    public CanvasView customCanvas;
    IServerConnector serverConnection;
    INearbyConnector nearbyConnection;
    Thread t;
    PositioningSetup currentSetup;
    long updateStartTime;
    long updateEndTime;
    long updateTimeElapsed;
    long updateRelativeTime = 0;
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
                        if(updateTimeElapsed < 1000000){
                            t.sleep((10000000 - updateTimeElapsed)/1000000);
                        }

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
        updateStartTime = System.nanoTime();
        //label.setText(serverConnection.getStatus());
        if (label.getText().equals("false")) {
            t.interrupt();
        }

        updateNodes();
        updateEndTime = System.nanoTime();
        updateTimeElapsed = updateEndTime - updateStartTime;
        updateRelativeTime += updateTimeElapsed;
    }

    private void updateNodes(){
        drawNodes.clear();
        for(TrackedNode node : currentSetup.getNodes().values()){
            TreeMap<Long, Coordinate> coordinates = node.getCoordinates();
            TreeMap.Entry<Long,Coordinate> entry = coordinates.higherEntry(updateRelativeTime);
            if(entry != null){drawNodes.add(entry.getValue());}
        }
        updateCanvas();
    }

    private void updateCanvas(){
        int minValue = currentSetup.getMinCoordinateValue();
        int maxValue = currentSetup.getMaxCoordinateValue();
        customCanvas.updateNodes(drawNodes, minValue, maxValue);
    }

    public void buttonConnectToServer(View view) {
        System.out.println("Button clicked");
        //connectToServer = !connectToServer;
    }

    private void initialize() {
        label = findViewById(R.id.outputLabel);
        button = findViewById(R.id.ServerButton);
        customCanvas = (CanvasView)findViewById(R.id.CanvasView);

        serverConnection = new ServerConnector();
        nearbyConnection = new NearbyConnectorFromFile(this);

        UUID id = nearbyConnection.NearbyLookup().keySet().iterator().next();
        currentSetup = nearbyConnection.getSetup(id);
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
