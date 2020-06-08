package com.example.positioningapp.NearbyConnector;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Unit;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataFormatterFromFile {

    private DataLoaderFromFile loader;
    private List<String> rawData;
    private List<Unit> allNodes = new ArrayList<>();

    private boolean initialized = false;

    public DataFormatterFromFile(){
        loader = new DataLoaderFromFile();
    }

    public List<Node> getUpdate(long elapsedTime){
        if(loader.isReady() && !initialized){ initialize(); }

        List<Node> currentNodes = new ArrayList<>();
        for(Unit unit : allNodes){
            //Change this method so it keeps track of elapsed time inside each node and perhaps index itself?
            Node node = unit.asNode();
            Node copyNode = node.copyNodeToTime(elapsedTime);
            copyNode.setPreviousIndex(copyNode.getCoordinates().size()-1);
            currentNodes.add(copyNode);
        }
        return currentNodes;
    }

    private void initialize(){
        rawData = loader.getUpdate();
        formatData();
        initialized = true;
    }

    public void stop(){
        loader.stop();
    }

    private void formatData(){
        for(String rawString : rawData){
            System.out.println("RawString: " + rawString);
            String[] rawArray = rawString.split(";");
            String id = rawArray[0];
            Coordinate newCoordinate = formatCoordinate(rawArray);
            Unit node = getNode(id);
            node.addCoordinate(newCoordinate);
        }
    }

    private Coordinate formatCoordinate(String[] rawArray){
        Coordinate newCoordinate = null;
        try{
            int x = Integer.parseInt(rawArray[1]);
            int y = Integer.parseInt(rawArray[2]);
            int z = Integer.parseInt(rawArray[3]);
            Date date = Constants.df.parse(rawArray[4].replace(".",":"));
            newCoordinate = new Coordinate(x,y,z);
            newCoordinate.setDateTime(date);
        }catch(Exception e){
            System.out.println(e);
            System.out.println(e.getStackTrace()[1]);
        }
        return newCoordinate;
    }


    //Gets the node from the list nodes. returns a new node if absent in the list
    private Unit getNode(String id){
        for(Unit unit : allNodes){
            if(unit.getId().equals(id)){
                return unit;
            }
        }

        Node newNode = new Node(id);
        allNodes.add(newNode);
        return newNode;
    }
}
