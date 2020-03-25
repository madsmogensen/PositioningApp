package com.example.positioningapp.Common.Data;

import java.util.Date;

public class Coordinate {

    private int x;
    private int y;
    private int z;
    private long dateTime;
    private long relativeTime;

    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getRelativeTime(){ return relativeTime; }

    public void setRelativeTime(long relativeTime){ this.relativeTime = relativeTime; }

    public long getDateTime(){ return dateTime; }

    public void setDateTime(long dateTime){ this.dateTime = dateTime; }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString(){
        return "Coordinate: " + x + "," + y + "," + z;
    }
}
