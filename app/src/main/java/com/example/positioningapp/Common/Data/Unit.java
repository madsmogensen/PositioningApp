package com.example.positioningapp.Common.Data;

public abstract class Unit {

    public String id;

    public String getId(){
        return id;
    }

    public abstract void update();

    public abstract void addCoordinate(Coordinate coordinate);

    public Node asNode(){
        if(this.isNode()){
            return (Node)this;
        }
        return null;
    }

    public boolean isNode(){
        if(getClass().equals(Node.class)){
            return true;
        }
        return false;
    }

}
