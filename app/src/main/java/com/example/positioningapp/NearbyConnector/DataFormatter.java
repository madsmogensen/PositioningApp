package com.example.positioningapp.NearbyConnector;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataFormatter {

    private DataLoader loader = new DataLoader();
    private List<String> rawData;
    private HashMap<String, Node> nodes = new HashMap<>();

    public DataFormatter(){

    }

    public HashMap<String, Node> getUpdate(){
        nodes = new HashMap<>();
        rawData = loader.getUpdate();
        if(rawData.size() > 0){
        }
        formatData();
        return nodes;
    }

    public void stop(){
        loader.stop();
    }

    private void formatData(){
        for(String rawString : rawData){
            String[] rawArray = rawString.split(";");
            String id = rawArray[0];
            Coordinate newCoordinate = formatCoordinate(rawArray);
            Node node = getNode(id);
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


    //Gets the node from the hashmap nodes. returns a new node if absent in the hashmap
    private Node getNode(String id){
        if(nodes.containsKey(id)){
            return nodes.get(id);
        }else{
            Node newNode = new Node();
            nodes.put(id, newNode);
            return newNode;
        }
    }
}
