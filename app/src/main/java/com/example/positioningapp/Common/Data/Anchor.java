package com.example.positioningapp.Common.Data;


public class Anchor extends Unit{
    private Coordinate coordinate;


    public Anchor(String id){
        this.id = id;
    }

    public void addCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    @Override
    public void update() {

    }
}
