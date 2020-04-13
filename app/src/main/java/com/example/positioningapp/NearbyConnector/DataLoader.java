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

public class DataLoader {

    private Thread t;
    private List<String> rawData = new ArrayList<>();
    private List<String> fullRawData = new ArrayList<>();
    private long lastTime = System.nanoTime();
    private int index = 0;
    private long avgTimeStep = 225*1000000; //millis to nano

    public DataLoader(){
        t = new Thread() {
            @Override
            public void run(){
                loadFile();
                try{
                    while(!isInterrupted()){
                        //If current time - last time > avg timestep, add next datapoint from fullraw to raw
                        if(System.nanoTime() - lastTime > avgTimeStep){
                            rawData.add(fullRawData.get(index%fullRawData.size()));
                            lastTime = System.nanoTime();
                            index++;
                        }
                        t.sleep(100);
                    }
                }catch(Exception e){
                    System.out.println("Error in thread t: run() from in DataLoader");
                    System.out.println(e);
                }
            }
        };
        t.start();
    }

    public List<String> getUpdate(){
        List<String> tempData = this.rawData;
        rawData = new ArrayList<>(); //replace list instead of .clear to make a new object reference
        return tempData;
    }

    public void stop(){
        t.interrupt();
    }

    private void loadFile(){
        //Load file
        String fileLocation = "uwb.csv";
        BufferedReader br;
        String line = "";
        try{
            br = new BufferedReader(new InputStreamReader(Constants.context.getAssets().open(fileLocation)));
            br.readLine(); //skip first line by reading it before while
            while((line = br.readLine()) != null){
                fullRawData.add(line);
            }
        }catch(Exception e){
            System.out.println("error in new file?");
            System.out.println(e);
            for(StackTraceElement stackTrace : e.getStackTrace()){
                System.out.println(stackTrace);
            }
        }
    }
}
