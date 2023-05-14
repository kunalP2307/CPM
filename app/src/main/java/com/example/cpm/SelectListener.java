package com.example.cpm;

import android.view.View;

import com.example.cpm.model.Activity;

public interface SelectListener {
    void onItemClick(View v,int position, Activity activity);

}
