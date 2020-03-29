package com.example.positioningapp.Common.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TreeMap;
import java.util.UUID;

public class TrackedNode {

    TreeMap<Date, Coordinate> coordinates = new TreeMap<>();
    UUID ID = UUID.randomUUID();
    String name;
    int index = 0;

    public TrackedNode(String name) {
        this.name = name != null ? name : ID.toString();
    }

    public TreeMap<Date, Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(TreeMap<Date, Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    //Add a new coordinate at the current time
    public void addCoordinate(Coordinate newCoordinate){
        updateCoordinateRelativeTime(newCoordinate);
        this.coordinates.put(newCoordinate.getDateTime(),newCoordinate);
    }

    //Add a new coordinate at a specified time
    public void addTimedCoordinate(LocalDateTime time, Coordinate coordinate){
        this.coordinates.put(new Date(System.nanoTime()),coordinate);
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
        try{
            if(coordinates.size() == 0){ newCoordinate.setRelativeTime(new Date(0)); return; }
            Date previousNodeTime = coordinates.lastKey();
            Date previousNodeRelativeTime = coordinates.lastEntry().getValue().getRelativeTime();
            Date newNodeTime = newCoordinate.getDateTime();
            Date timeElapsed = new Date(newNodeTime.getTime()-previousNodeTime.getTime());
            newCoordinate.setRelativeTime(new Date(previousNodeRelativeTime.getTime()+timeElapsed.getTime()));
        }catch(Exception e){
            System.out.println("Error in updateCoordinateRelativeTime");
            System.out.println(e);
        }

    }
}
