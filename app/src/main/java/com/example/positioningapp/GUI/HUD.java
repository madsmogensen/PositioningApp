package com.example.positioningapp.GUI;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.positioningapp.Common.Interface.ActionListener;

import java.util.ArrayList;
import java.util.List;

public class HUD extends View {

    public TextView label;
    public Button button;
    public List<ActionListener> listeners = new ArrayList<>();

    public HUD(Context context, LinearLayout layout) {
        super(context);
        label = new TextView(context);
        label.setText("Hello World!!!");

        button = new Button(context);
        button.setText("Stop nearby connection");
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);
        button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                //ToDo here
                for(ActionListener listener : listeners){
                    listener.actionPerformed();
                }
            }
        });

        layout.addView(label);
        layout.addView(button);
    }

    public void addActionListener(ActionListener newListener){
        listeners.add(newListener);
    }
}
