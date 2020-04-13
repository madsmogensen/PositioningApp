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
}
