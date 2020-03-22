package com.example.positioningapp.Common.Data;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.UUID;

public class TrackedNode {

    TreeMap<LocalDateTime, Coordinate> coordinates = new TreeMap<>();
    UUID ID = UUID.randomUUID();
    String name;

    public TrackedNode(String name){
        this.name = name;
    }




}
