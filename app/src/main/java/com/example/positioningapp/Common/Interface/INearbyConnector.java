package com.example.positioningapp.Common.Interface;

import com.example.positioningapp.Common.Data.PositioningSetup;
import com.example.positioningapp.Common.Data.TrackedNode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface INearbyConnector {
    HashMap<UUID,String> NearbyLookup();
    PositioningSetup getSetup(UUID setupID);
}
