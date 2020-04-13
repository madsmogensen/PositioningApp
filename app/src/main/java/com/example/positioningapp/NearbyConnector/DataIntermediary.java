package com.example.positioningapp.NearbyConnector;

import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.INearbyConnector;

import java.util.HashMap;
import java.util.Map;

public class DataIntermediary implements INearbyConnector {

    DataFormatter formatter = new DataFormatter();
    HashMap<String, Node> data;

    public DataIntermediary(){

    }

    @Override
    public void getUpdate(Setup setup){
        data = formatter.getUpdate();
        //add data to setup
        HashMap<String, Node> nodes = setup.getNodes();
        for(Map.Entry<String, Node> entry : data.entrySet()){
            if(nodes.containsKey(entry.getKey())){
                //Add the coordinate set of the new node to the original
                nodes.get(entry.getKey()).plus(entry.getValue());
            }else{
                //Add the new node to the setup
                nodes.put(entry.getKey(),entry.getValue());
            }
        }
    }

    public void stop(){
        formatter.stop();
    }
}
