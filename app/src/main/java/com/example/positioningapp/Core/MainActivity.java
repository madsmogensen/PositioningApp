package com.example.positioningapp.Core;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Data.Node;
import com.example.positioningapp.Common.Data.Setup;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.Common.Interface.IGUI;
import com.example.positioningapp.Common.Interface.INearbyConnector;
import com.example.positioningapp.Common.Interface.IServerConnector;
import com.example.positioningapp.GUI.GuiIntermediary;
import com.example.positioningapp.NearbyConnector.DataIntermediary;
import com.example.positioningapp.R;

import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity implements ActionListener {

    IServerConnector serverConnection;
    INearbyConnector nearbyConnector;
    IGUI GUI;
    Thread t;
    Setup currentSetup = new Setup();
    Handler mHandler;
    int mInterval = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        mHandler = new Handler();
        startRepeatingTask();
        /*//Thread to run the while loop
        t = new Thread() {
            @Override
            public void run(){
                try{
                    while(!isInterrupted()){
                        update();
                        t.sleep(100);
                    }
                }catch(Exception e){
                    System.out.println("Error in thread t: run() from onCreate");
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        };
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();*/
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

    void startRepeatingTask(){
        mUpdater.run();
    }

    int i = 0;
    private void update() {
        //if(i < 10){
            nearbyConnector.getUpdate(currentSetup);
            GUI.update(currentSetup);
            //i++;
        //}

    }

    public void buttonConnectToServer(View view) {
        System.out.println("Button clicked");
        nearbyConnector.stop();
        //t.interrupt();
    }

    private void initialize() {
        //Set constants in Common
        System.out.println("initializing");
        Constants.context = this;
        Constants.mainLayout = findViewById(R.id.MainLayout);

        //Instantiate other Modules
        //serverConnection = new ServerConnector();
        nearbyConnector = new DataIntermediary();
        GUI = new GuiIntermediary(this);
    }

    @Override
    public void actionPerformed(){ buttonConnectToServer(null); }

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
