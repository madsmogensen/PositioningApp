package com.example.positioningapp.NearbyConnector;

import com.example.positioningapp.Common.Data.PositioningSetup;
import com.example.positioningapp.Common.Data.TrackedNode;
import com.example.positioningapp.Common.Interface.INearbyConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NearbyConnectorFromFile implements INearbyConnector {

    private HashMap<UUID, PositioningSetup> setups = new HashMap<>();
    private UUID connectedToID;
    private Thread t;

    @Override
    public void NearbyLookup() {
        //look for the file in res
    }

    public void connectToSetup(int ID){
        //Create PositioningSetup
        PositioningSetup setupFromFile = new PositioningSetup("FromFile");
        //Add anchors

        //Load node positions
        List<TrackedNode> nodes = loadFile();

        setups.put(setupFromFile.getID(),setupFromFile);

        /*t = new Thread() {
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
        connectedToID = setupFromFile.getID();
    }

    public void connectToSetup(String name){

    }

    //returns the nodes with at their current positions
    public List<TrackedNode> getPositions(){
        List<TrackedNode> nodes = new ArrayList<>();
        PositioningSetup setup = setups.get(connectedToID);

        return null;
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
                TrackedNode newNode = new TrackedNode(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]), null);
                nodes.add(newNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }




}
