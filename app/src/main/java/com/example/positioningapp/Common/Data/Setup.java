package com.example.positioningapp.Common.Data;

import java.util.HashMap;

public class Setup {

    private HashMap<String, Node> nodes = new HashMap<>();
    private HashMap<String, Anchor> anchors = new HashMap<>();

    public Setup(){

    }

    public HashMap<String, Node> getNodes(){
        return nodes;
    }

    public HashMap<String, Anchor> getAnchors(){
        return anchors;
    }

    public void updateSetup(){
        for(Node node : nodes.values()){
            node.updateNode();
        }
    }

    public void pauseSetup(){
        for(Node node : nodes.values()){
            node.pauseNode();
        }
    }

    public void rewindSetup(){
        for(Node node : nodes.values()){
            node.rewindNode();
        }
    }

    public void fastForwardSetup(){
        for(Node node : nodes.values()){
            node.fastForwardNode();
        }
    }

    public void playSetup(long elapsedTime){
        for(Node node : nodes.values()){
            node.playNode(elapsedTime);
        }
    }

    public void backOnce(){
        for(Node node : nodes.values()){
            node.backOnce();
        }
    }

    public void forwardOnce(){
        for(Node node : nodes.values()){
            node.forwardOnce();
        }
    }
}
