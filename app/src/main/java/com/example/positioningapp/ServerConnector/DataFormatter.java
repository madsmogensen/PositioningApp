package com.example.positioningapp.ServerConnector;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Coordinate;
import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Data.Unit;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class DataFormatter implements ICallback {

    private UDPIncoming incoming;
    private UDPOutgoing outgoing;
    private int port = 11000;

    public DataFormatter() {
        incoming = new UDPIncoming(port);
        outgoing = new UDPOutgoing(port);
    }

    public void sendMessage(String msg){
        outgoing.sendMessage(msg);
    }

    public void stopConnection(){
        incoming.stop();
        outgoing.stop();
    }

    public void update(Setup setup, List<String> setupList){
        //Update the setup with the new data from incoming
        List<String> temp = incoming.getTempData();
        for (String data : temp){
            String[] dataList = data.split(";");
            switch(dataList[0]){
                case "SETUP":
                    setupList.add(dataList[1]);
                    showDialog(this, setupList);
                    break;
                case "DATA":
                    try {
                        //DATA;ID;X;Y;Z;DATETIME
                        String id = dataList[1];
                        int x = Integer.parseInt(dataList[2]);
                        int y = Integer.parseInt(dataList[3]);
                        int z = Integer.parseInt(dataList[4]);
                        //DATETIME
                        Date date = Constants.dfs.parse(dataList[5].replace(".",":"));
                        Coordinate newCoordinate = new Coordinate(x,y,z);
                        newCoordinate.setDateTime(date);
                        Unit node = getNode(setup, id);
                        node.addCoordinate(newCoordinate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        for(Unit node : setup.getNodes().values()){
            node.sortCoordinates();
        }
    }

    public void showDialog(final ICallback callback, final List<String> setupList){
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Constants.context);
        builder.setTitle("Select setup");
        String selected = "";

        // add a list
        String[] setups = setupList.toArray(new String[setupList.size()]);
        builder.setItems(setups, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("ITEM SELECTED: " + setupList.get(which));
                callback.callback(setupList.get(which));
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void callback(String setupID) {
        //setup = new Setup(setupID);
        System.out.println("Trying to send message with CLIENT;REQUEST:id");
        outgoing.sendMessage("CLIENT;REQUEST:" + setupID);
    }

    //Gets the node from the list nodes. returns a new node if absent in the list
    private Unit getNode(Setup setup, String id){
        for(Unit unit : setup.getNodes().values()){
            if(unit.getId().equals(id)){
                return unit;
            }
        }
        Node newNode = new Node(id);
        setup.addNode(newNode);
        System.out.println("new node added to setup! new size: " + setup.getNodes().size());
        return newNode;
    }
}

interface ICallback {
    void callback(String setupID);
}
