package com.example.positioningapp.Common.Data;

public class UnitFactory {

    public Unit makeUnit(String id) throws Exception {
        switch(id.charAt(1)){
            case 'x':
                return new Node(id);
            case 'z':
                return new Anchor(id);
            default:
                throw new Exception("Error in UnitFactory.makeUnit!");
        }
    }
}
