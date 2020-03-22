package com.example.positioningapp.NearbyConnector;

import com.example.positioningapp.Common.Data.PositioningSetup;
import com.example.positioningapp.Common.Data.TrackedNode;
import com.example.positioningapp.Common.Interface.INearbyConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NearbyConnectorFromFile implements INearbyConnector {

    private HashMap<UUID, PositioningSetup> setups = new HashMap<>();
    private Thread t;

    @Override
    public HashMap<UUID,String> NearbyLookup() {
        //returns the UUID name pairs of nearby setups
        return null;
    }

    //returns the nodes with at their current positions
    public PositioningSetup getPositions(UUID setupID, LocalDateTime time){
        PositioningSetup setup = setups.get(setupID);
        List<TrackedNode> nodes = setup.getNodes(time);
        return setups.get(setupID);
    }

    private List<TrackedNode> loadFile(){
        List<TrackedNode> nodes = new ArrayList<>();
        //Load whole file
        String fileLocation = "";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        try {
            br = new BufferedReader(new FileReader(fileLocation));
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(cvsSplitBy);
                //TrackedNode newNode = new TrackedNode(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]), null);
                //nodes.add(newNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }




}
/*//Create PositioningSetup
        PositioningSetup setupFromFile = new PositioningSetup("FromFile");
        //Add anchors

        //Load node positions
        List<TrackedNode> nodes = loadFile();

        setups.put(setupFromFile.getID(),setupFromFile);

        t = new Thread() {
            @Override
            public void run(){
                try{
                    while(!isInterrupted()){
                        Thread.sleep(10);
                        //play on repeat
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        };*/