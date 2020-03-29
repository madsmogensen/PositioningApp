package com.example.positioningapp.NearbyConnector;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.PositioningSetup;
import com.example.positioningapp.Common.Data.TrackedNode;
import com.example.positioningapp.Common.Interface.INearbyConnector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.Date;

public class NearbyConnectorFromFile implements INearbyConnector {

    private HashMap<UUID, PositioningSetup> setups = new HashMap<>();
    private Thread t;

    public NearbyConnectorFromFile(Context context){
        PositioningSetup setupFromFile = new PositioningSetup("FileSetup");
        setups.put(setupFromFile.getID(), setupFromFile);
        loadFile(setupFromFile, context);
    }

    //returns the UUID name pairs of nearby setups
    @Override
    public HashMap<UUID,String> NearbyLookup() {
        HashMap<UUID, String> idNamePairs = new HashMap<>();
        for (UUID key : setups.keySet()){
            idNamePairs.put(key, setups.get(key).getName());
        }
        return idNamePairs;
    }

    //returns the nodes with at their current positions
    public PositioningSetup getSetup(UUID setupID){
        return setups.get(setupID);
    }

    @Override
    public Date getStartTime() {
        return null;
    }


    //Load file is not loading the file as one node with multiple timed coords, but all the cords are localtime.now
    private void loadFile(PositioningSetup setup, Context context){
        TrackedNode newNode = new TrackedNode("TestNode");
        //Load file
        String fileLocation = "uwb.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        try{
            br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileLocation)));
            br.readLine(); //skip first line by reading it before while
            while((line = br.readLine()) != null){
                String[] splitLine = line.split(cvsSplitBy);
                Coordinate newCoordinate = retrieveDataFromLine(splitLine);
                newNode.addCoordinate(newCoordinate);
            }
            setup.addNode(newNode);
            /*for(Coordinate cord : newNode.getCoordinates().values()){
                System.out.println(cord.toString());
            }*/
        }catch(Exception e){
            System.out.println("error in new file?");
            System.out.println(e);
            for(StackTraceElement stackTrace : e.getStackTrace()){
                System.out.println(stackTrace);
            }
        }
    }

    private Coordinate retrieveDataFromLine(String[] splitLine){
        int x = Integer.parseInt(splitLine[1]);
        int y = Integer.parseInt(splitLine[2]);
        int z = Integer.parseInt(splitLine[3]);
        Coordinate newCoord = new Coordinate(x,y,z);
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            //System.out.println("As string: " + splitLine[4].replace(".",":"));
            Date coordinateDate = df.parse(splitLine[4].replace(".",":"));
            //System.out.println("As Date  : " + df.format(coordinateDate));
            newCoord.setDateTime(coordinateDate);
            //System.out.println("Get Date : " + df.format(newCoord.getDateTime()));
        }catch(Exception e){
            System.out.println(e);
            System.out.println(e.getStackTrace()[1]);
        }
        return newCoord;
    }

}