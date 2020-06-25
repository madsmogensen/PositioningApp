package com.example.positioningapp.Common.Data;

import java.util.Date;

public class Coordinate implements Comparable<Coordinate>{

    private int x;
    private int y;
    private int z;
    private Date dateTime;
    private Date relativeTime;

    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int compareTo(Coordinate other){
        if(dateTime.getTime() > other.dateTime.getTime()){
            return 1;
        }
        else if(dateTime.getTime() < other.dateTime.getTime()){
            return -1;
        }
        else{
            return 0;
        }
    }

    public Date getRelativeTime(){ return relativeTime; }

    public void setRelativeTime(Date relativeTime){ this.relativeTime = relativeTime; }

    public Date getDateTime(){ return dateTime; }

    public void setDateTime(Date dateTime){ this.dateTime = dateTime; }

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
