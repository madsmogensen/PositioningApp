package com.example.positioningapp.Common.Data;

import java.util.UUID;

public class Anchor {
    int x;
    int y;
    int z;
    String name;
    UUID ID = UUID.randomUUID();

    public Anchor(int x, int y, int z, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
}
