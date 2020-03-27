package com.example.positioningapp.Common.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PositioningSetup {

    private String name = "";
    private UUID ID = UUID.randomUUID();
    private HashMap<UUID, Anchor> anchorPositions = new HashMap<>();
    private HashMap<UUID, TrackedNode> nodePositions = new HashMap<>();


    public PositioningSetup(String name){
        this.name = name;
    }

    public PositioningSetup(String name, UUID ID){
        this.name = name;
        this.ID = ID;
    }

    public PositioningSetup(UUID ID){
        this.ID = ID;
    }

    public void addAnchor(int x, int y, int z, String name){
        //If name is null, set name as anchor count
        int anchorCount = anchorPositions.size();
        name = name != null ? name : String.valueOf(anchorCount);
        Anchor newAnchor = new Anchor(x,y,z,name);
        anchorPositions.put(newAnchor.ID,newAnchor);
    }

    public void addNode(TrackedNode node){
        nodePositions.put(node.getID(), node);
    }

    public UUID getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public HashMap<UUID, TrackedNode> getNodes() {
        //returns the nodes at the current time of the setup
        return nodePositions;
    }

    public int getMinCoordinateValue(){
        int minValue = Integer.MAX_VALUE;
        for(TrackedNode node : nodePositions.values()){
            for(Coordinate coordinate : node.getCoordinates().values()){
                minValue = Math.min(minValue, Math.min(coordinate.getX(),coordinate.getY()));
            }
        }
        return minValue;
    }

    public int getMaxCoordinateValue(){
        int maxValue = Integer.MIN_VALUE;
        for(TrackedNode node : nodePositions.values()){
            for(Coordinate coordinate : node.getCoordinates().values()){
                maxValue = Math.max(maxValue, Math.max(coordinate.getX(),coordinate.getY()));
            }
        }
        return maxValue;
    }

}
