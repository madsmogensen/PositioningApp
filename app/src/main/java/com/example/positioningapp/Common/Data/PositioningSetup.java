package com.example.positioningapp.Common.Data;

import java.util.HashMap;
import java.util.UUID;

public class PositioningSetup {

    private String name = "";
    private UUID ID = UUID.randomUUID();
    private HashMap<UUID, Anchor> anchorPositions = new HashMap<>();


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

    public UUID getID(){
        return ID;
    }

    public String getName(){
        return name;
    }
}
