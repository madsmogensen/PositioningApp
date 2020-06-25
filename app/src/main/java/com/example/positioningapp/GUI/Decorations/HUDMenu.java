package com.example.positioningapp.GUI.Decorations;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.positioningapp.Common.Data.Constants;
import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.GUI.HUDDecorator;
import com.example.positioningapp.R;

import java.util.ArrayList;
import java.util.List;

public class HUDMenu extends HUDDecorator {

    private Context context;
    private ConstraintLayout mainLayout;
    private List<ActionListener> listeners = new ArrayList<>();
    private ConstraintLayout cLayout;
    private ConstraintSet constraints;
    private List<String> buttonEvents;
    private Resources res;

    public HUDMenu(ActionListener listener, List<String> buttonEvents) {
        super(buttonEvents, listener);
        addActionListener(listener);
        this.context = super.getContext();
        this.mainLayout = super.getMainLayout();
        this.buttonEvents = buttonEvents;
        res = context.getResources();

        constraints = new ConstraintSet();

        cLayout = new ConstraintLayout(context);
        cLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
        cLayout.setId(View.generateViewId());

        constraints.constrainHeight(cLayout.getId(), ConstraintSet.WRAP_CONTENT);
        constraints.constrainWidth(cLayout.getId(), ConstraintSet.WRAP_CONTENT);

        cLayout.setConstraintSet(constraints);

        this.mainLayout.addView(cLayout);

        addMenuIcon();
    }

    private void addMenuIcon(){
        LinearLayout mainMenuLayout = new LinearLayout(context);
        mainMenuLayout.setOrientation(LinearLayout.VERTICAL);
        mainMenuLayout.setId(View.generateViewId());

        List<Button> buttonsInMainMenu = new ArrayList<>();

        Button nearbyMenu = newButton("Nearby Menu","NEARBYMENU",null);
        nearbyMenu.setBackgroundResource(R.drawable.icon_nearby);
        Button serverMenu = newButton("Server Menu","SERVERMENU",null);
        serverMenu.setBackgroundResource(R.drawable.icon_global);

        buttonsInMainMenu.add(nearbyMenu);
        buttonsInMainMenu.add(serverMenu);

        Button mainMenuButton = newButton("Main Menu Button","", buttonsInMainMenu);

        mainMenuLayout.addView(mainMenuButton);
        mainMenuLayout.addView(nearbyMenu);
        mainMenuLayout.addView(serverMenu);

        cLayout.addView(mainMenuLayout);

        constraints.clone(cLayout);
        constraints.connect(mainMenuLayout.getId(), ConstraintSet.LEFT, cLayout.getId(), ConstraintSet.LEFT, 8);
        constraints.connect(mainMenuLayout.getId(), ConstraintSet.TOP, cLayout.getId(), ConstraintSet.TOP, 8);
        cLayout.setConstraintSet(constraints);
    }

    private Button newButton(final String text, final String event, final List<Button> showHideButtons){
        Button newButton = new Button(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(87,87);
        newButton.setLayoutParams(params);
        newButton.setBackgroundResource(R.drawable.menu_icon);
        newButton.setId(View.generateViewId());
        newButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                //System.out.println("button clicked " + text);

                if(showHideButtons != null){
                    for(Button btn : showHideButtons){
                        btn.setClickable(!btn.isClickable());
                        btn.setVisibility(btn.isClickable()? View.VISIBLE : View.INVISIBLE);
                    }
                }
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
