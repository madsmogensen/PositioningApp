package com.example.positioningapp.Common.Data;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.UUID;

public class TrackedNode {

    TreeMap<LocalDateTime, Coordinate> coordinates = new TreeMap<>();
    UUID ID = UUID.randomUUID();
    String name;
    int index = 0;

    public TrackedNode(String name) {
        this.name = name != null ? name : ID.toString();
    }

    public TreeMap<LocalDateTime, Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(TreeMap<LocalDateTime, Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    //Add a new coordinate at the current time
    public void addCoordinate(Coordinate newCoordinate){
        this.coordinates.put(LocalDateTime.now(),newCoordinate);
    }

    //Add a new coordinate at a specified time
    public void addTimedCoordinate(LocalDateTime time, Coordinate coordinate){
        this.coordinates.put(time,coordinate);
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


}
