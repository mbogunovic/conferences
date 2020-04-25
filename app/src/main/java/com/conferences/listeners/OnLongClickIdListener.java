package com.conferences.listeners;

import android.view.View;

public class OnLongClickIdListener implements View.OnLongClickListener
{
    public String id;

    public OnLongClickIdListener(String id) {
        this.id = id;
    }

    @Override
    public boolean onLongClick(View v) { return true; }
};
