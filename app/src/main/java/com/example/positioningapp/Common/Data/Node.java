package com.example.positioningapp.Common.Data;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private List<Coordinate> coordinates = new ArrayList<>();

    public Node(){

    }

    public void addCoordinate(Coordinate coordinate){
        coordinates.add(coordinate);
    }

    public List<Coordinate> getCoordinates(){
        return coordinates;
    }

    public void plus(Node node){
        this.coordinates.addAll(node.getCoordinates());
    }

}
