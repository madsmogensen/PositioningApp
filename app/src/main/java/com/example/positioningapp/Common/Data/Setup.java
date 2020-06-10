package com.example.positioningapp.Common.Data;

import java.util.HashMap;

public class Setup {

    private HashMap<String, Unit> units = new HashMap<>();

    public HashMap<String, Node> getNodes(){
        HashMap<String, Node> nodes = new HashMap<>();
        for(Unit unit : units.values()){
            if(unit.id.charAt(1) == 'x'){
                nodes.put(unit.id,unit.asNode());
            }
        }
        return nodes;
    }

    public HashMap<String, Unit> getAnchors(){
        HashMap<String, Unit> anchors = new HashMap<>();
        for(Unit unit : units.values()){
            if(unit.id.charAt(1) == 'z'){
                anchors.put(unit.id,unit);
            }
        }
        return anchors;
    }

    public void updateSetup(){
        for(Unit node : units.values()){
            node.update();
        }
    }

    public void pauseSetup(){
        for(Unit unit : units.values()){
            if(!unit.isNode()){continue;}
            Node node = unit.asNode();
            node.pauseNode();
        }
    }

    public void rewindSetup(){
        for(Unit unit : units.values()){
            if(!unit.isNode()){continue;}
            Node node = unit.asNode();
            node.rewindNode();
        }
    }

    public void fastForwardSetup(){
        for(Unit unit : units.values()){
            if(!unit.isNode()){continue;}
            Node node = unit.asNode();
            node.fastForwardNode();
        }
    }

    public void playSetup(long elapsedTime){
        for(Unit unit : units.values()){
            if(!unit.isNode()){continue;}
            Node node = unit.asNode();
            node.playNode(elapsedTime);
        }
    }

    public void backOnce(){
        for(Unit unit : units.values()){
            if(!unit.isNode()){continue;}
            Node node = unit.asNode();
            node.backOnce();
        }
    }

    public void forwardOnce(){
        for(Unit unit : units.values()){
            if(!unit.isNode()){continue;}
            Node node = unit.asNode();
            node.forwardOnce();
        }
    }
}
