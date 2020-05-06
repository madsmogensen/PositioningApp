package com.example.positioningapp.GUI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.positioningapp.Common.Interface.ActionListener;
import com.example.positioningapp.R;

import java.util.ArrayList;
import java.util.List;

public class HUDBottomPanel extends View {

    private Context context;
    private ConstraintLayout mainLayout;
    private List<ActionListener> listeners = new ArrayList<>();
    private ConstraintLayout cLayout;
    private ConstraintSet constraints;
    private List<String> buttonEvents;
    private Resources res;

    public HUDBottomPanel(Context context, ConstraintLayout mainLayout, List<String> buttonEvents) {
        super(context);
        this.context = context;
        this.mainLayout = mainLayout;
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

        addBottomPanel();
    }

    private void addBottomPanel(){
        LinearLayout bottomPanel = new LinearLayout(context);
        bottomPanel.setOrientation(LinearLayout.HORIZONTAL);
        bottomPanel.setGravity(Gravity.RIGHT);
        bottomPanel.setId(View.generateViewId());

        List<Button> buttonsInPause = new ArrayList<>();
        List<Button> allButtonsInMenu = new ArrayList<>();

        Button backOnce = newButton("<-", "BACKONCE", null, R.drawable.icon_play);
        buttonsInPause.add(backOnce);
        allButtonsInMenu.add(backOnce);

        Button forwardOnce = newButton("->", "FORWARDONCE", null, R.drawable.icon_play);
        buttonsInPause.add(forwardOnce);
        allButtonsInMenu.add(forwardOnce);

        Button pause = newButton("Pause", "PAUSE", buttonsInPause, R.drawable.icon_play);
        allButtonsInMenu.add(pause);

        Button play = newButton("Play", "PLAY", null, R.drawable.icon_play);
        allButtonsInMenu.add(play);

        Button live = newButton("Live", "LIVE", null, R.drawable.icon_play);
        allButtonsInMenu.add(live);

        Button rewind = newButton("Rewind", "REWIND", null, R.drawable.icon_play);
        allButtonsInMenu.add(rewind);

        Button fastForward = newButton("Fast Forward", "FASTFORWARD", null, R.drawable.icon_play);
        allButtonsInMenu.add(fastForward);

        Button menuButton = newButton("menu","", allButtonsInMenu, R.drawable.menu_icon);

        bottomPanel.addView(menuButton);
        bottomPanel.addView(rewind);
        bottomPanel.addView(fastForward);
        bottomPanel.addView(live);
        bottomPanel.addView(play);
        bottomPanel.addView(pause);
        bottomPanel.addView(forwardOnce);
        bottomPanel.addView(backOnce);

        cLayout.addView(bottomPanel);

        constraints.clone(cLayout);
        constraints.connect(bottomPanel.getId(), ConstraintSet.LEFT, cLayout.getId(), ConstraintSet.LEFT, 8);
        constraints.connect(bottomPanel.getId(), ConstraintSet.BOTTOM, cLayout.getId(), ConstraintSet.BOTTOM, 8);
        cLayout.setConstraintSet(constraints);
    }

    private Button newButton(final String text, final String event, final List<Button> showHideButtons, int drawable){
        Button newButton = new Button(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(87,87);
        newButton.setLayoutParams(params);
        newButton.setBackgroundResource(drawable);
        newButton.setId(View.generateViewId());
        newButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("button clicked " + text);

                if(showHideButtons != null){
                    boolean first = true;
                    boolean show = true;
                    for(Button btn : showHideButtons){
                        if(first){
                            first = false;
                            show = !btn.isClickable();
                        }
                        btn.setClickable(show);
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
