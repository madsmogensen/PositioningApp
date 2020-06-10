package com.example.positioningapp.Core;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.Common.Interface.IGUI;
import com.example.positioningapp.Common.Interface.INearbyConnector;
import com.example.positioningapp.Common.Interface.IServerConnector;
import com.example.positioningapp.GUI.GuiIntermediary;
import com.example.positioningapp.NearbyConnector.NearbyDataIntermediary;
import com.example.positioningapp.ServerConnector.ServerDataIntermediary;
import com.example.positioningapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity implements ActionListener {

    private IServerConnector serverConnection;
    private INearbyConnector nearbyConnector;
    private IGUI GUI;
    private Setup currentSetup = new Setup();
    private Handler mHandler;
    private int mInterval = 100;
    private State state = State.LIVE;
    private List<String> buttonEvents = new ArrayList<>();

    private long lastTime = System.currentTimeMillis();
    private long elapsedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        mHandler = new Handler();
        startRepeatingTask();
    }

    Runnable mUpdater = new Runnable(){
        @Override
        public void run(){
            try{
                update();
            }finally{
                mHandler.postDelayed(mUpdater,mInterval);
            }
        }
    };

    private void startRepeatingTask(){
        mUpdater.run();
    }

    private void update() {
        elapsedTime = System.currentTimeMillis()-lastTime;
        lastTime = System.currentTimeMillis();
        switch(state){
            case LIVE:
                currentSetup.updateSetup();
                break;
            case REWIND:
                currentSetup.rewindSetup();
                break;
            case FASTFORWARD:
                currentSetup.fastForwardSetup();
                break;
            case PLAY:
                currentSetup.playSetup(elapsedTime);
                break;
        }
        nearbyConnector.getUpdate(currentSetup, elapsedTime);
        GUI.update(currentSetup);
    }

    private void buttonPause(){
        System.out.println("PAUSE Button clicked");
        if(state == State.LIVE){
            currentSetup.pauseSetup();
        }
        state = State.PAUSE;
    }

    public void buttonLive(){
        System.out.println("LIVE Button clicked");
        state = State.LIVE;
    }

    public void buttonRewind(){
        System.out.println("REWIND Button clicked");
        state = State.REWIND;
    }

    public void buttonPlay(){
        System.out.println("PLAY Button clicked");
        state = State.PLAY;
    }

    public void buttonFastForward(){
        System.out.println("FASTFORWARD Button clicked");
        state = State.FASTFORWARD;
    }

    public void buttonBackOnce(){
        System.out.println("BACK Button clicked");
        currentSetup.backOnce();
        state = State.PAUSE;
    }

    public void buttonForwardOnce(){
        System.out.println("FORWARD Button clicked");
        currentSetup.forwardOnce();
        state = State.PAUSE;
    }

    private void initialize() {
        //Set constants in Common
        System.out.println("initializing");
        Constants.context = this;
        Constants.mainLayout = findViewById(R.id.MainLayout);

        //Instantiate other Modules
        serverConnection = new ServerDataIntermediary();
        serverConnection.sendMessage("CLIENT;REQUEST:From File");
        nearbyConnector = new NearbyDataIntermediary();
        GUI = new GuiIntermediary(this, buttonEvents);
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void actionPerformed(){
        for(String event : buttonEvents){
            switch(event.replaceAll(" ", "")){
                case "REWIND":
                    buttonRewind();
                    break;
                case "PAUSE":
                    buttonPause();
                    break;
                case "LIVE":
                    buttonLive();
                    break;
                case "PLAY":
                    buttonPlay();
                    break;
                case "FASTFORWARD":
                    buttonFastForward();
                    break;
                case "BACKONCE":
                    buttonBackOnce();
                    break;
                case "FORWARDONCE":
                    buttonForwardOnce();
                    break;
            }
        }
        buttonEvents.clear();
    }

    private IServerConnector registerIServerConnector() {
        ServiceLoader<IServerConnector> loader = null;
        IServerConnector instance = null;
        try{
            loader = ServiceLoader.load(IServerConnector.class);
            System.out.println("loader has next? " + loader.iterator().hasNext());
        }catch(Exception e){
            System.out.println("Error in registerIServerConnector Method 1 " + e);
        }
        try{
            instance = loader.iterator().next().getClass().newInstance();
        }catch(Exception e){
            System.out.println("Error in registerIServerConnector Method 2 " + e);
        }
        return instance;
    }

    private INearbyConnector registerINearbyConnector(){
        ServiceLoader<INearbyConnector> loader = null;
        INearbyConnector instance = null;
        try{
            loader = ServiceLoader.load(INearbyConnector.class);
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            instance = loader.iterator().next().getClass().newInstance();
        }catch(Exception e){
            System.out.println(e);
        }
        return instance;
    }
}
