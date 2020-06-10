package com.example.positioningapp.NearbyConnector;

import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Data.Unit;
import com.example.positioningapp.Common.Interface.INearbyConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearbyDataIntermediary implements INearbyConnector {

    private DataFormatterFromFile formatter = new DataFormatterFromFile();
    private List<Unit> data;

    @Override
    public void getUpdate(Setup setup, long elapsedTime){
        data = formatter.getUpdate(elapsedTime);
        //add data to setup
        HashMap<String, Node> nodes = setup.getNodes();
        for(Unit unit : data){
            Node node = (Node)unit;
            if(nodes.containsKey(node.getId())){
                nodes.get(node.getId()).plus(node);
            }else{
                nodes.put(node.getId(),node);
            }
        }
    }

    public void stop(){
        formatter.stop();
    }
}
