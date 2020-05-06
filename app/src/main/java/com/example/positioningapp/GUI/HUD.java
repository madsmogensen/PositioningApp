package com.example.positioningapp.GUI;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.positioningapp.Common.Interface.ActionListener;

import java.util.ArrayList;
import java.util.List;

public class HUD extends View {

    private Context context;
    private ConstraintLayout mainLayout;
    private List<ActionListener> listeners = new ArrayList<>();
    private ConstraintLayout cLayout;
    private ConstraintSet constraints;
    private List<String> buttonEvents;

    public HUD(Context context, ConstraintLayout mainLayout, List<String> buttonEvents) {
        super(context);
        this.context = context;
        this.mainLayout = mainLayout;
        this.buttonEvents = buttonEvents;

        constraints = new ConstraintSet();

        cLayout = new ConstraintLayout(context);
        cLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        cLayout.setId(View.generateViewId());

        constraints.constrainHeight(cLayout.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.constrainWidth(cLayout.getId(), ConstraintSet.WRAP_CONTENT);

        cLayout.setConstraintSet(constraints);

        this.mainLayout.addView(cLayout);

        addLabel();
        addBottomPanel();
    }

    private void addLabel(){
        TextView label = new TextView(context);
        label.setText("Hello World!!!");
        label.setGravity(Gravity.CENTER);
        label.setId(View.generateViewId());

        label.setId(View.generateViewId());

        cLayout.addView(label);
    }

    private void addBottomPanel(){
        LinearLayout bottomPanel = new LinearLayout(context);
        bottomPanel.setOrientation(LinearLayout.VERTICAL);
        bottomPanel.setId(View.generateViewId());

        LinearLayout bottomPanelTop = new LinearLayout(context);
        bottomPanelTop.setOrientation(LinearLayout.HORIZONTAL);
        bottomPanelTop.setGravity(Gravity.RIGHT);
        bottomPanelTop.setId(View.generateViewId());
        bottomPanel.addView(bottomPanelTop);

        LinearLayout bottomPanelBottom = new LinearLayout(context);
        bottomPanelBottom.setOrientation(LinearLayout.HORIZONTAL);
        bottomPanelBottom.setId(View.generateViewId());
        bottomPanel.addView(bottomPanelBottom);



        bottomPanelTop.addView(newButton("<-", "BACKONCE"));
        bottomPanelTop.addView(newButton("->", "FORWARDONCE"));
        Button placeholderButton = new Button(context);
        placeholderButton.setAlpha(0);
        bottomPanelTop.addView(placeholderButton);
        bottomPanelTop.addView(newButton("LIVE", "LIVE"));
        bottomPanelBottom.addView(newButton("REWIND", "REWIND"));
        bottomPanelBottom.addView(newButton("FAST FORWARD", "FASTFORWARD"));
        bottomPanelBottom.addView(newButton("PAUSE", "PAUSE"));
        bottomPanelBottom.addView(newButton("PLAY", "PLAY"));


        cLayout.addView(bottomPanel);

        constraints.clone(cLayout);
        constraints.centerHorizontally(bottomPanel.getId(), cLayout.getId());
        constraints.connect(bottomPanel.getId(), ConstraintSet.BOTTOM, cLayout.getId(), ConstraintSet.BOTTOM, 8);
        cLayout.setConstraintSet(constraints);
    }

    private Button newButton(final String text, final String event){
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button newButton = new Button(context);
        newButton.setText(text);
        newButton.setLayoutParams(lp);
        newButton.setId(View.generateViewId());
        newButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                for(ActionListener listener : listeners){
                    buttonEvents.add(event);
                    listener.actionPerformed();
                }
            }
        });
        return newButton;
    }



    public void addActionListener(ActionListener newListener){
        listeners.add(newListener);
    }
}
