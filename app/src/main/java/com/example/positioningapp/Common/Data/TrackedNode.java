package com.example.positioningapp.Common.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class TrackedNode {

    int currentX;
    int currentY;
    int currentZ;
    UUID ID = UUID.randomUUID();
    String name;
    LocalDateTime time;

    public TrackedNode(int x, int y, int z, LocalDateTime time){
        this.currentX = x;
        this.currentY = y;
        this.currentZ = z;
        this.time = time != null ? this.time : LocalDateTime.now();
    }


}
