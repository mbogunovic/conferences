package com.conferences.listeners;

import android.view.View;

public class OnClickIdListener implements View.OnClickListener
{
    public String id;

    public OnClickIdListener(String id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) { }
};
