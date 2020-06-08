package com.example.positioningapp.Common.Data;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private List<Coordinate> coordinates = new ArrayList<>();
    private int currentIndex = 0;
    private long elapsedTime = 0;
    private long lastTime = 0;
    private String id;
    private int previousIndex = 0;

    public Node(){
    }

    public Node(String id){
        this.id = id;
    }

    public void addCoordinate(Coordinate coordinate){
        coordinates.add(coordinate);
    }

    public List<Coordinate> getCoordinates(){
        return coordinates;
    }

    public Coordinate getCoordinate(int i){
        return coordinates.get(i);
    }

    public void plus(Node node){
        this.coordinates.addAll(node.getCoordinates());
    }

    public void updateNode(){
        if(coordinates.isEmpty()){
            currentIndex = 0;
        }else{
            currentIndex = coordinates.size()-1;
        }
    }

    public void pauseNode(){
        currentIndex = coordinates.size()-1;
    }

    public void rewindNode(){
        if(currentIndex != 0){
            currentIndex--;
        }
    }

    public void fastForwardNode(){
        if(currentIndex < coordinates.size()-1){
            currentIndex++;
        }
    }

    public void playNode(long elapsedTime){
        //Not implemented yet
        //compare the time passed since last time this was done with the next node
        this.elapsedTime += elapsedTime;

        while(true){
            if(currentIndex == coordinates.size()-1){ break; } //we've caught up to live?

            long timeBetweenCoordinates = coordinates.get(currentIndex+1).getDateTime().getTime() - coordinates.get(currentIndex).getDateTime().getTime();
            //If looping used time between last two points as time between last and first point:
            if(timeBetweenCoordinates < 0){
                timeBetweenCoordinates = coordinates.get(currentIndex).getDateTime().getTime() - coordinates.get(currentIndex-1).getDateTime().getTime();
            }
            if(timeBetweenCoordinates <= this.elapsedTime){
                currentIndex++;
                this.elapsedTime -= timeBetweenCoordinates;
            }else{
                break;
            }
        }
    }

    public void backOnce(){
        rewindNode();
    }

    public void forwardOnce(){
        fastForwardNode();
    }

    public int getIndex(){
        return currentIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPreviousIndex() {
        return previousIndex;
    }

    public void setPreviousIndex(int previousIndex) {
        this.previousIndex = previousIndex;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Node copyNodeToTime(long timeElapsed){
        Node newNode = new Node(id);
        if(coordinates.isEmpty()){ return newNode; }
        elapsedTime += timeElapsed;
        while(true){
            //first run or looped around
            if(currentIndex == 0){
                newNode.addCoordinate(coordinates.get(0));
                currentIndex++;
                lastTime = coordinates.get(0).getDateTime().getTime();
            }

            Coordinate currentCoord = coordinates.get(currentIndex);
            //check if enough time has elapsed to get another coordinate
            long timeBetweenCoordinates = currentCoord.getDateTime().getTime()-lastTime;
            if(elapsedTime - timeBetweenCoordinates >= 0){
                elapsedTime -= timeBetweenCoordinates;
                newNode.addCoordinate(currentCoord);
                currentIndex++;
                currentIndex = currentIndex % coordinates.size();
                lastTime = currentCoord.getDateTime().getTime();
            }else{
                break;
            }
        }
        return newNode;
    }
}
