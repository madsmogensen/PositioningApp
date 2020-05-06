package com.example.positioningapp.NearbyConnector;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Data.TrackedNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataLoaderFromFile {

    private Thread t;
    private List<String> fullRawData = new ArrayList<>();
    private boolean ready = false;

    public DataLoaderFromFile(){
        t = new Thread() {
            @Override
            public void run(){
                loadFile();
                ready = true;
            }
        };
        t.start();
    }

    public List<String> getUpdate(){
        return fullRawData;
 }

    public void stop(){
        t.interrupt();
    }

    public boolean isReady(){
        return ready;
    }

    private void loadFile(){
        //Load file
        //String fileLocation = "uwb.csv";
        String fileLocation = "uwb_GoCart.csv";
        BufferedReader br;
        String line = "";
        try{
            br = new BufferedReader(new InputStreamReader(Constants.context.getAssets().open(fileLocation)));
            br.readLine(); //skip first line by reading it before while
            while((line = br.readLine()) != null){
                fullRawData.add(line);
            }
            System.out.println("DONE READING THE FILE: " + fullRawData.size() + " lines read");
        }catch(Exception e){
            System.out.println("error in new file?");
            System.out.println(e);
            for(StackTraceElement stackTrace : e.getStackTrace()){
                System.out.println(stackTrace);
            }
        }
    }
}
