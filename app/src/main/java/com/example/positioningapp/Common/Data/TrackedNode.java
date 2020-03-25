package com.example.positioningapp.Common.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TreeMap;
import java.util.UUID;

public class TrackedNode {

    TreeMap<Long, Coordinate> coordinates = new TreeMap<>();
    UUID ID = UUID.randomUUID();
    String name;
    int index = 0;

    public TrackedNode(String name) {
        this.name = name != null ? name : ID.toString();
    }

    public TreeMap<Long, Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(TreeMap<Long, Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    //Add a new coordinate at the current time
    public void addCoordinate(Coordinate newCoordinate){
        this.coordinates.put(System.currentTimeMillis(),newCoordinate);
        updateCoordinateRelativeTime(newCoordinate);
    }

    //Add a new coordinate at a specified time
    public void addTimedCoordinate(LocalDateTime time, Coordinate coordinate){
        this.coordinates.put(System.currentTimeMillis(),coordinate);
    }

    public UUID getID() {
        return ID;
    }

    public int getIndex(){
        return index;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void updateCoordinateRelativeTime(Coordinate newCoordinate){
        if(coordinates.size() == 0){ newCoordinate.setRelativeTime(0); return; }
        long previousNodeTime = coordinates.lastKey();
        long previousNodeRelativeTime = coordinates.lastEntry().getValue().getRelativeTime();
        long newNodeTime = newCoordinate.getDateTime();
        long timeElapsed = newNodeTime-previousNodeTime;
        newCoordinate.setRelativeTime(previousNodeRelativeTime+timeElapsed);
    }
}
